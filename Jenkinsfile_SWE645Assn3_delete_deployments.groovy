pipeline {

    agent any
    stages {
        stage('Deleting the deployments and services of both Frontend and Backend containers on Kubernetes cluster') {
            steps{
                script{
                          sh'''
                        #!/bin/bash
                        ls -ltr

                        withCredentials([string(credentialsId: 'argocd_host', variable: 'host'), usernamePassword(credentialsId: 'argocd_cred', passwordVariable: 'pass', usernameVariable: 'id')]) {
                            sh 'argocd login $host  --username $id --password $pass --insecure '
                            sh 'argocd app delete swe645assn3cd  --cascade -y 2> /dev/null '
                        }

                        '''
                }
            }
        }

    }
}
     


