pipeline {
    agent any
    
    environment {
        DOCKER_COMPOSE_VERSION = '2.20.0'
        GITHUB_REPO = 'https://github.com/wmanzr/SmartHomeWebApp.git'
    }
    
    tools {
        maven 'Maven 3.9'
        jdk 'JDK 21'
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo '📥 Клонируем репозиторий...'
                git branch: 'main', url: "${GITHUB_REPO}"
            }
        }
        
        stage('Build Contracts') {
            steps {
                echo '📦 Собираем контракты (API + Events)...'
                script {
                    sh '''
                        cd smart-home-contract || echo "No contract module"
                        if [ -f pom.xml ]; then
                            mvn clean install -DskipTests
                        fi
                    '''
                    
                    sh '''
                        cd smart-home-events-contract || echo "No events contract"
                        if [ -f pom.xml ]; then
                            mvn clean install -DskipTests
                        fi
                    '''
                }
            }
        }
        
        stage('Build Services') {
            parallel {
                stage('Build Command Service') {
                    steps {
                        echo '🔨 Собираем Command Service...'
                        sh '''
                            cd smart-home-command-service
                            mvn clean package -DskipTests
                        '''
                    }
                }

                stage('Build Analytics Service') {
                    steps {
                        echo '🔨 Собираем Analytics Service...'
                        sh '''
                            cd smart-home-analytics-service
                            mvn clean package -DskipTests
                        '''
                    }
                }

                stage('Build REST Service') {
                    steps {
                        echo '🔨 Собираем REST Service...'
                        sh '''
                            cd smart-home
                            mvn clean package -DskipTests
                        '''
                    }
                }
                
                stage('Build Audit Service') {
                    steps {
                        echo '🔨 Собираем Audit Service...'
                        sh '''
                            cd smart-home-audit-service
                            mvn clean package -DskipTests
                        '''
                    }
                }
                
                stage('Build Notification Service') {
                    steps {
                        echo '🔨 Собираем Notification Service...'
                        sh '''
                            cd notification-service
                            mvn clean package -DskipTests
                        '''
                    }
                }
            }
        }
    
        
        stage('Build Docker Images') {
            steps {
                echo '🐳 Собираем Docker образы...'
                sh '''
                    docker-compose build --parallel
                '''
            }
        }
        
        stage('Stop Old Containers') {
            steps {
                echo '🛑 Останавливаем старые контейнеры...'
                sh '''
                    docker-compose down || true
                '''
            }
        }
        
        stage('Deploy') {
            steps {
                echo '🚀 Разворачиваем приложение...'
                sh '''
                    docker-compose up -d
                '''
            }
        }
        
        stage('Health Check') {
            steps {
                echo '❤️ Проверяем здоровье сервисов...'
                script {
                    sleep(time: 30, unit: 'SECONDS')
                    
                    // Проверка REST API
                    sh '''
                        curl -f http://localhost:8080/actuator/health || exit 1
                    '''
                    
                    // Проверка других сервисов
                    sh '''
                        curl -f http://localhost:8081/actuator/health || echo "Audit service not ready"
                        curl -f http://localhost:8082/actuator/health || echo "Notification service not ready"
                    '''
                }
            }
        }
    }
    
    post {
        success {
            echo '✅ Pipeline выполнен успешно!'
            // Можно добавить уведомление в Slack, Email и т.д.
        }
        
        failure {
            echo '❌ Pipeline провалился!'
            // Уведомление об ошибке
        }
        
        always {
            echo '🧹 Очистка...'
            // Очистка временных файлов
            cleanWs()
        }
    }
}