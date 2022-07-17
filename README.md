# 645Assn3_CICD #Vamsi Krishna Peddi

This repo contains scripts related to Jenkins, Kubernetes and agrocd to do CICD for assignment3

1. swe645assn3_ArgoCD_config.yaml :  Argocd app creation using jenkins job

2. Jenkinsfile_SWE645Assn3_pipeline.groovy : This file is used by jenkins pipeline job to build the containers and also deploying the containers on kubernetes cluster

3. Jenkinsfile_SWE645Assn3_delete_deployments.groovy : This file is used by jenkins pipeline job to delete the deployments on kubernetes cluster

4. argocd/swe645_assn3_surveyform_full_stack_deployment_config.yaml : Argocd will deply configuration present in this directory /argocd and currently it will deploy configuration given in swe645_assn3_surveyform_full_stack_deployment_config.yaml  
