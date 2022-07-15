pipeline {

    agent any

        stage('Deleting the deployments and services of both Frontend and Backend containers on Kubernetes cluster') {
            steps{
                script{
                        sh'''
                        #!/bin/bash
                        ls -ltr
                        kubectl delete deployment survey-deployment-be survey-deployment-ui --ignore-not-found=true
                        kubectl delete svc surveyservicebe surveyserviceui --ignore-not-found=true
                        '''
                }
            }
        }

    }

     
}

