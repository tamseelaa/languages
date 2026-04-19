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
		JAVA_HOME = 'C:\\Program Files\\Java\\jdk-21'  // Adjust to your actual JDK pat
        SONARQUBE_SERVER = 'SonarQubeServer'  // The name of the SonarQube server configured in Jenkins
        SONAR_TOKEN = 'squ_36ddcb7129eea21de9e6ef95dd0081b14220ecce'
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

		stage('Run Tests') {
			steps {
				bat 'mvn clean test'
			}
		}

		stage('Code Coverage') {
			steps {
				bat 'mvn jacoco:report'
			}
		}

		stage('Publish Test Results') {
			steps {
				junit '**/target/surefire-reports/*.xml'
			}
		}

		stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQubeServer') {
                    bat """
                                ${tool 'SonarScanner'}\\bin\\sonar-scanner
                                -Dsonar.projectKey=Shoppingcart
                                -Dsonar.sources=src
                                -Dsonar.projectName=Shoppingcart
                                -Dsonar.host.url=http://localhost:9000
                                -Dsonar.login=${env.SONAR_TOKEN}
                                -Dsonar.java.binaries=target/classes
                            """
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