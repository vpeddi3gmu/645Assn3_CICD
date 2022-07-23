//Vamsi Krishna Peddi

//This file is used by jenkins pipeline job which will build and deploy the 
//both frontend and backend containers on kubernetes cluster using argocd


pipeline {
    environment {
        docker_credentails = 'docker_cred_645_assn3'
        docker_backend = "vpeddi3/swe645assn3"
        docker_build = ''
        docker_frontend = "vpeddi3/swe645assn3ui"
    }
    agent any
    

    // Frontend code Build Phase

    stages {
        stage('Cloning Git Frontend Code') {
            steps{
                echo 'Cloning Git for Frontend Code..'
                git credentialsId: 'gitlogin_token', url: 'https://github.com/vpeddi3gmu/645Assn3_UI.git'
            }
        }

        stage('Building and Containerizing the Frontend Code ') {
            steps {
                echo 'Building and Containerizing the Frontend Code..'
                script {
                        sh'''
                        #!/bin/bash
                        ls -ltr
                        '''
                  docker_build = docker.build(docker_frontend + ":latest")
                }

            }
        }

        
        stage('Pusing Frontend Image to Docker repo') {
            steps{
                echo 'Pusing Frontend Image to Docker repo..'
                script{
                    docker.withRegistry('',docker_credentails){
                        sh 'docker tag $docker_frontend:latest $docker_frontend:$BUILD_NUMBER'
                        sh 'docker push $docker_frontend:latest'
                        sh 'docker push $docker_frontend:$BUILD_NUMBER'
                    }
                }
            }
        }





        // Backend code Build Phase

         stage('Cloning Git for Backend Code') {
            steps{
                echo 'Cloning Git for Backend Code..'
                git credentialsId: 'gitlogin_token', url: 'https://github.com/vpeddi3gmu/645Assn3_BE.git'          
            }
        }

        stage('Building and Containerizing the Backend Code') {
            steps {
                echo 'Building and Containerizing the Backend Code..'
                script {
                        sh'''
                        #!/bin/bash
                        ls -ltr
                        '''
                  docker_build = docker.build(docker_backend + ":latest")
                }

            }
        }

        
        stage('Pusing Backend Image to Docker repo') {
            steps{
                 echo 'Pusing Backend Image to Docker repo..'
                script{
                    docker.withRegistry('',docker_credentails){
                        sh 'docker tag $docker_backend:latest $docker_backend:$BUILD_NUMBER'
                        sh 'docker push $docker_backend:latest'
                        sh 'docker push $docker_backend:$BUILD_NUMBER'
                    }
                }
            }
        }


        // Argocd deployment phase

        stage('Cloning Git for Deploying continers on Kubernetes cluster') {
            steps{
                echo 'Cloning Git for Deploying continers on Kubernetes cluster..'
                git credentialsId: 'gitlogin_token', url: 'https://github.com/vpeddi3gmu/645Assn3_CICD.git'
                
                
            }
        }

        stage('Deploying both Frontend and Backend containers on Kubernetes cluster') {
            steps{
                    sh'''
                        #!/bin/bash
                        ls -ltr
                    '''
                script{

                        withCredentials([string(credentialsId: 'argocd_host', variable: 'host'), usernamePassword(credentialsId: 'argocd_cred', passwordVariable: 'pass', usernameVariable: 'id')]) {
                            sh 'argocd login $host  --username $id --password $pass --insecure'
                            //sh 'argocd app delete swe645assn3cd   --cascade -y 2> /dev/null  || true '
                            sh 'argocd app create -f swe645assn3_ArgoCD_config.yaml --upsert'
                        }

                        
                }
            }
        }

    }

     
}

