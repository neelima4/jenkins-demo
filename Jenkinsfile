pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'neelima4/jenkins-demo'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE:${BUILD_NUMBER} -t $DOCKER_IMAGE:latest .'
            }
        }

        stage('Docker Login and Push') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-credentials',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )
                ]) {
                    sh '''
                        echo "$DOCKER_PASS" | docker login \
                        -u "$DOCKER_USER" \
                        --password-stdin

                        docker push $DOCKER_IMAGE:${BUILD_NUMBER}
			docker push $DOCKER_IMAGE:latest
                    '''
                }
            }
        }


        stage('Deploy with Docker Compose') {
            steps {
                sh '''
                    docker compose pull
	            docker compose down
                    docker compose up -d
                '''
            }
        }
    }
}
