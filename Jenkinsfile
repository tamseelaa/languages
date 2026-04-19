pipeline {
    agent any

    options {
        skipDefaultCheckout(true)
    }

    tools {
        maven 'Maven3911'
    }

    environment {
        PATH = "C:\\Program Files\\Docker\\Docker\\resources\\bin;${env.PATH}"
        JAVA_HOME = 'C:\\Program Files\\Java\\jdk-21'
        SONARQUBE_SERVER = 'SonarQubeServer'
        DOCKERHUB_CREDENTIALS_ID = 'DOCKER-HUB'
        DOCKERHUB_REPO = 'tamseela/languages'
        DOCKER_IMAGE_TAG = 'v1'
    }

    stages {

        stage('Checkout') {
            steps {
                git(
                    url: 'https://github.com/tamseelaa/languages',
                    branch: 'main'
                )
            }
        }


        stage('SonarQube Analysis') {
            def mvn = tool 'Default Maven';
            withSonarQubeEnv() {
              sh "${mvn}/bin/mvn clean verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=Shoppingcart -Dsonar.projectName='Shoppingcart'"
            }
          }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}")
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKERHUB_CREDENTIALS_ID) {
                        docker.image("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}").push()
                    }
                }
            }
        }
    }
}