pipeline {
    agent any

    tools {
        maven 'Maven 3.9'
        jdk 'JDK-21'
    }

    stages {
        stage('Build') {
            steps {
                sh 'which java || true'
                sh 'java -version'
                sh 'javac -version'
                sh 'echo "JAVA_HOME=$JAVA_HOME"'
                sh 'mvn -v'
                sh 'mvn -B clean install -DskipTests'
            }
        }

        stage('Docker Compose Build') {
            steps {
                script {
                    sh 'docker --version'
                    sh 'docker compose --version || docker compose version'
                    sh 'docker compose -f docker-compose.services.yml down --remove-orphans || true'
                    sh 'docker compose -f docker-compose.services.yml build --no-cache'
                    sh 'docker compose -f docker-compose.services.yml up -d'
                }
            }
        }
    }

    post {
        success { echo ' ПОБЕДА !! Все сервисы собраны и запущены !' }
        failure { echo 'Ошибка сборки или запуска :(' }
        always { cleanWs() }
    }
}