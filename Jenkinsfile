pipeline {
    agent any

    environment {
        DOCKER_NETWORK = "movierecommendation_default"
        MYSQL_HOST = "movie-db"
        MYSQL_PORT = "3306"
        MYSQL_USER = "root"
        MYSQL_PASSWORD = "redditSucks769170186"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git 'your-repo-url.git'  // Replace with your Git repository
            }
        }

        stage('Build and Test') {
            steps {
                script {
                    withEnv(["SPRING_DATASOURCE_URL=jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/movie_db?useSSL=false"]) {
                        sh './mvnw clean test'
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t movierecommendation-movie-service .'
            }
        }

        stage('Run Container') {
            steps {
                sh 'docker run --rm --network ${DOCKER_NETWORK} movierecommendation-movie-service'
            }
        }
    }

    post {
        always {
            echo 'Pipeline execution completed!'
        }
    }
}
