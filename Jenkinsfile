pipeline {
    agent any
 environment {
        APP_NAME = 'jenkins-demo'
        APP_ENV = 'dev'
    }

    stages {

        stage('Build') {
            steps {
                echo 'Building application...'
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Running unit tests...'
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                echo 'Creating JAR...'
                sh 'mvn package -DskipTests'
            }
        }

           stage('Deploy') {
            steps {
                echo "Deploying ${APP_NAME} to ${APP_ENV}"
                sh '''
                    mkdir -p /tmp/jenkins-deploy
                    cp target/*.jar /tmp/jenkins-deploy/
                    ls -lh /tmp/jenkins-deploy/
                '''

    		}

    	 }
    }
    post {
        success {
            echo 'Build completed successfully!'
            archiveArtifacts artifacts: 'target/*.jar'
        }

        failure {
            echo 'Build failed!'
        }
    }
}
