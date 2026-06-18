pipeline {
    agent any

    options {
        skipDefaultCheckout(true)
    }

    environment {
        XRAY_PROJECT_KEY = 'JAV'
    }

    stages {
        stage('Checkout') {
            steps {
                deleteDir()
                checkout scm
                sh 'find src/test/resources/features -maxdepth 3 -type f | sort || true'
            }
        }

        stage('API Tests') {
            agent {
                docker {
                    image 'maven:3.9.9-eclipse-temurin-24'
                    args '-v $HOME/.m2:/root/.m2'
                }
            }
            steps {
                sh 'mvn clean test -Papi'

                withCredentials([
                    string(credentialsId: 'xray-client-id', variable: 'XRAY_CLIENT_ID'),
                    string(credentialsId: 'xray-client-secret', variable: 'XRAY_CLIENT_SECRET')
                ]) {
                    sh '''
                        export XRAY_EXECUTION_SUMMARY="TAS API Smoke Run"
                        export XRAY_EXECUTION_DESCRIPTION="API execution imported from TAS"
                        export XRAY_PROJECT_KEY="${XRAY_PROJECT_KEY}"

                        
                    '''
                }
            }
            post {
                always {
                    stash name: 'api-surefire', includes: 'target/surefire-reports/**/*.xml', allowEmpty: true
                    stash name: 'api-allure', includes: 'target/allure-results/**', allowEmpty: true
                }
            }
        }

        stage('UI Tests') {
            steps {
                sh '''
                    docker network inspect tas-net >/dev/null 2>&1 || docker network create tas-net
                    docker rm -f selenium-chrome >/dev/null 2>&1 || true
                    docker run -d --name selenium-chrome \
                      --network tas-net \
                      --shm-size=2g \
                      selenium/standalone-chrome
                    sleep 15
                '''

                catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                    sh '''
                        docker run --rm \
                          --network tas-net \
                          --volumes-from jenkins \
                          -v /root/.m2:/root/.m2 \
                          -w /var/jenkins_home/workspace/ob-tas-java \
                          maven:3.9.9-eclipse-temurin-24 \
                          mvn clean test -Pui,remote  \
                            -Dremote.url=http://selenium-chrome:4444/wd/hub
                    '''
                }

                withCredentials([
					    string(credentialsId: 'xray-client-id', variable: 'XRAY_CLIENT_ID'),
					    string(credentialsId: 'xray-client-secret', variable: 'XRAY_CLIENT_SECRET')
					]) {
					    sh '''
					        docker run --rm \
					          --network tas-net \
					          --volumes-from jenkins \
					          -v /root/.m2:/root/.m2 \
					          -w /var/jenkins_home/workspace/ob-tas-java \
					          -e XRAY_CLIENT_ID=$XRAY_CLIENT_ID \
					          -e XRAY_CLIENT_SECRET=$XRAY_CLIENT_SECRET \
					          -e XRAY_PROJECT_KEY=$XRAY_PROJECT_KEY \
					          maven:3.9.9-eclipse-temurin-24 \
					          bash -c "
					            export XRAY_EXECUTION_SUMMARY='TAS UI Smoke Run'
					            export XRAY_EXECUTION_DESCRIPTION='UI execution imported from TAS'
					
					            
					          "
					    '''
					}
            }
            post {
                always {
                    stash name: 'ui-surefire', includes: 'target/surefire-reports/**/*.xml', allowEmpty: true
                    stash name: 'ui-allure', includes: 'target/allure-results/**', allowEmpty: true
                }
            }
        }

        stage('Publish Reports') {
            steps {
                unstash 'api-surefire'
                unstash 'api-allure'
                unstash 'ui-surefire'
                unstash 'ui-allure'

                junit testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: true

                allure([
                    includeProperties: false,
                    jdk: '',
                    commandline: 'allure',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }

    post {
        always {
            sh 'docker rm -f selenium-chrome >/dev/null 2>&1 || true'
            sh 'docker network rm tas-net >/dev/null 2>&1 || true'
        }
    }
}
