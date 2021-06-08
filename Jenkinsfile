pipeline {
    agent any
     tools {
        jdk 'Java'
    }
    stages {
        stage('build') {
            steps {
                sh 'mvn --version'
	        sh 'mvn clean compile'
	        sh 'mvn test'
	        
            }
        }
    }
}
