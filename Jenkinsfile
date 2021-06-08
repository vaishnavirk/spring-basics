pipeline {
    agent any 
    tools
    { 
        jdk 'JDK11' 
    }
    stages {
        stage('build') {
            steps {
                sh 'mvn --version'
            }
        }
    }
}
