pipeline {
    environment {
        docker_credentails = 'docker_cred_645_assn3'
        docker_backend = "vpeddi3/swe645assn3"
        docker_build = ''
        docker_frontend = "vpeddi3/swe645assn3ui"
    }
    agent any
    
    stages {
        stage('Cloning Git Frontend Code') {
            steps{
                echo 'Cloning Git for Frontend Code..'
                git 'https://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@github.com/vpeddi3gmu/645Assn3_UI.git'
                
                
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






         stage('Cloning Git for Backend Code') {
            steps{
                echo 'Cloning Git for Backend Code..'
                git 'https://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@github.com/vpeddi3gmu/645Assn3_BE.git'
                
                
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



        stage('Cloning Git for Deploying continers on Kubernetes cluster') {
            steps{
                echo 'Cloning Git for Deploying continers on Kubernetes cluster..'
                git 'https://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@github.com/vpeddi3gmu/645Assn3_CICD.git'
                
                
            }
        }

        stage('Deploying both Frontend and Backend containers on Kubernetes cluster') {
            steps{
                script{
                        sh'''
                        #!/bin/bash
                        ls -ltr
                        kubectl delete deployment survey-deployment-be survey-deployment-ui
                        kubectl delete svc surveyservicebe surveyserviceui
                        kubectl create -f swe645_assn3_surveyform_full_stack_deployment_config.yaml
                        '''
                }
            }
        }

    }

     
}

