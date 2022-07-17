//Vamsi Krishna Peddi

//This file is used by jenkins pipeline job which will undeploy
//both frontend and backend containers on kubernetes cluster using argocd

pipeline {

    agent any
    stages {
        stage('Deleting the deployments and services of both Frontend and Backend containers on Kubernetes cluster') {
            steps{
                
                sh'''
                    #!/bin/bash
                    ls -ltr
                '''
                script{
                    withCredentials([string(credentialsId: 'argocd_host', variable: 'host'), usernamePassword(credentialsId: 'argocd_cred', passwordVariable: 'pass', usernameVariable: 'id')]) {
                        sh 'argocd login $host  --username $id --password $pass --insecure '
                        sh 'argocd app delete swe645assn3cd  --cascade -y 2> /dev/null || true'
                    }
                }
            }
        }

    }
}
     


