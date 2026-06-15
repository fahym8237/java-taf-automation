pipeline {
    agent any

    options {
        skipDefaultCheckout(true)
    }

    environment {
        XRAY_PROJECT_KEY = 'JAS'

        LOGIN = 'https://opencart.liveblog365.com/index.php?route=account/login&language=en-gb'
        FORGOTTEN = 'https://opencart.liveblog365.com/index.php?route=account/forgotten&language=en-gb'
        REGISTER = 'https://opencart.liveblog365.com/index.php?route=account/register&language=en-gb'

        ACCOUNT = 'https://opencart.liveblog365.com/index.php?route=account/account&language=en-gb'
        EDIT_ACCOUNT = 'https://opencart.liveblog365.com/index.php?route=account/edit&language=en-gb'
        CHANGE_PASSWORD = 'https://opencart.liveblog365.com/index.php?route=account/password&language=en-gb'
        LOGOUT = 'https://opencart.liveblog365.com/index.php?route=account/logout&language=en-gb'

        HOME_PAGE = 'https://opencart.liveblog365.com/index.php?route=common/home&language=en-gb'
        CHECKOUT = 'https://opencart.liveblog365.com/index.php?route=checkout/checkout&language=en-gb'
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
		            args '--entrypoint="" -v $HOME/.m2:/root/.m2'
		            reuseNode true
		        }
		    }

		    steps {
		        catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
		            sh 'mvn clean test -Papi'
		        }
		    }

		    post {
		        always {
		            stash name: 'api-surefire', includes: 'target/surefire-reports/**/*.xml', allowEmpty: true
		            stash name: 'api-allure', includes: 'target/allure-results/**', allowEmpty: true
		            archiveArtifacts artifacts: 'target/api/**/*.json,target/allure-results/**', allowEmptyArchive: true
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

                withCredentials([
                    string(credentialsId: 'login-password', variable: 'LOGIN_PASSWORD'),
                    string(credentialsId: 'login-email', variable: 'LOGIN_EMAIL'),
                    string(credentialsId: 'login-new-password', variable: 'LOGIN_NEW_PASSWORD')
                ]) {
                    catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                        sh '''
                            docker run --rm \
                              --network tas-net \
                              --volumes-from jenkins \
                              -v /root/.m2:/root/.m2 \
                              -w /var/jenkins_home/workspace/java-test-automation \
                              -e LOGIN_PASSWORD="$LOGIN_PASSWORD" \
                              -e LOGIN_EMAIL="$LOGIN_EMAIL" \
                              -e LOGIN_NEW_PASSWORD="$LOGIN_NEW_PASSWORD" \
                              -e LOGIN="$LOGIN" \
                              -e FORGOTTEN="$FORGOTTEN" \
                              -e REGISTER="$REGISTER" \
                              -e ACCOUNT="$ACCOUNT" \
                              -e EDIT_ACCOUNT="$EDIT_ACCOUNT" \
                              -e CHANGE_PASSWORD="$CHANGE_PASSWORD" \
                              -e LOGOUT="$LOGOUT" \
                              -e HOME_PAGE="$HOME_PAGE" \
                              -e CHECKOUT="$CHECKOUT" \
                              maven:3.9.9-eclipse-temurin-24 \
                              mvn clean test -Psuite,remote \
                                -Dremote.url=http://selenium-chrome:4444/wd/hub
                        '''
                    }
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
                          -e XRAY_CLIENT_ID="$XRAY_CLIENT_ID" \
                          -e XRAY_CLIENT_SECRET="$XRAY_CLIENT_SECRET" \
                          -e XRAY_PROJECT_KEY="$XRAY_PROJECT_KEY" \
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

		        junit testResults: 'target/surefire-reports/**/*.xml', allowEmptyResults: true

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
