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

		stage('Publish Coverage Report') {
			steps {
				jacoco()
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