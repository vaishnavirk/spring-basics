pipeline
{
    agent any
     tools {
        jdk 'JDK11'
    }
    environment {
        FULL_PATH_BRANCH = "${sh(script:'git name-rev --name-only HEAD', returnStdout: true)}"
        CURRENT_BRANCH = FULL_PATH_BRANCH.substring(FULL_PATH_BRANCH.lastIndexOf('/') + 1, FULL_PATH_BRANCH.length())
        TARGET_BRANCH = 'development'
        GIT_CLONE_URL = "https://del.tools.publicis.sapient.com/bitbucket/scm/psiab/orchestrator-service-pod3.git"
        registry = "orchestrator-service"
        registryCredential = 'ecr:us-east-1:ecr_access'
        ECR_URL = 'https://090097299954.dkr.ecr.us-east-1.amazonaws.com'
    }
    stages {
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
        stage('jacoco') {
            steps {
                echo 'Creating JaCoCo code coverage Reports'
                jacoco()
            }
        }
		stage('sonarqube') {
            steps {
                echo 'Conducting Sonar Qube Analysis'
                sh  '''
                mvn sonar:sonar \
                -Dsonar.projectKey=PSIAB:Orchestrator-Service \
                -Dsonar.host.url=https://tools.publicis.sapient.com/sonar \
                -Dsonar.login=178e5a484f500ac882137737a00b345ec6247c6f \
                -Dsonar.branch.name=$BRANCH_NAME \
                -Dsonar.branch.target=$BRANCH_TARGET \
                -Dsonar.tests=src/test \
                -Dsonar.language=java \
                -Dsonar.sources=src/main \
                -Dsonar.sourceEncoding=ISO-8859-1 \
                -Dproject.reporting.outputEncoding=UTF-8 \
                -Dsonar.surefire.reportsPath=target/surefire-reports \
                -Dsonar.coverage.jacoco.xmlReportPaths=target/jacoco-ut/jacoco.xml \
                -Dsonar.verbose=true
                '''
            }
        }
        stage('Deploy to Prod') {
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
        stage('Deploy to Dev') {
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