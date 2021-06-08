pipeline
{
    agent any
     tools {
        java jdk
    }
	
	stages 
	{
        stage('build') {
            steps {
                echo 'Building the source'
                sh 'mvn clean compile'
            }
        }
        stage('test') {
            steps {
                echo 'Testing source'
                sh 'mvn test'
            }
        }
		stage('Deploy to Prod') 
		{
            when {
                anyOf{
                    branch 'master';
                    branch 'release'
                }
            }
            steps {
                script {
                    sh 'mvn package -Pprod '
                    dockerImage = docker.build registr
                    docker.withRegistry( ECR_URL, registryCredential ) {
                        dockerImage.push()
                    }
                    sh "docker rmi $registr"
                }
            }
        }
        stage('Deploy to Dev') 
		{
            when {
                anyOf{
                    branch 'development';
                    branch 'feature-*';
                    branch 'PSIAB-*'
                }
            }
            steps {
                script {
                    sh 'mvn package -Pprod '
                    dockerImage = docker.build registry
                    docker.withRegistry( ECR_URL, registryCredential ) {
                        dockerImage.push()
                    }
                    sh "docker rmi $registry"
                }
            }
        }
	}
	post {
        always {
            echo 'JENKINS PIPELINE'
            junit 'target/surefire-reports/*.xml'
        }
        success {
            echo 'JENKINS PIPELINE SUCCESSFUL'
           // archiveArtifacts artifacts: 'target/*.*', onlyIfSuccessful: true
        }
        failure {
            echo 'JENKINS PIPELINE FAILED'
        }
        unstable {
            echo 'JENKINS PIPELINE WAS MARKED AS UNSTABLE'
        }
        changed {
            echo 'JENKINS PIPELINE STATUS HAS CHANGED SINCE LAST EXECUTION'
        }
    }
}