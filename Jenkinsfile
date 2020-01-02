pipeline {
    options {
        buildDiscarder(logRotator(numToKeepStr: '20'))
        disableConcurrentBuilds()
    }

    parameters {
        string(name: "BRANCH", description: "Branch to build from", defaultValue: "master")
        string(name: "HOSTNAME", description: "Override default hostname", defaultValue: "https://www.seleniumeasy.com/test/")
        string(name: "SUITE_FILE", description: "Specify TestNG Suite XML file under src/test/resources/suites/", defaultValue: "")
        choice(name: 'BROWSER', description: "NOTE: In order to run driver different from PhantomJS, you need an agent with GUI and browser installed", choices: 'phantomjs\nchrome\nfirefox\nopera\nie\nedge')
    }

    agent any

    stages{

        stage('Checkout') {
            steps {
                script {
                    echo "Checking out the project"
                }
                checkout([$class: 'GitSCM', branches: [[name: "${params.BRANCH}"]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/shenderov/web-automation-starter-java.git']]])
            }
        }

        stage ('Test') {
            steps {
                script {
                    echo "Start test running..."
                    sh "./gradlew clean test -Pdriver_type=${params.BROWSER} -Phostname=${params.HOSTNAME} -PsuiteFile=${params.SUITE_FILE}"
                }
            }
        }
    }

    post {
        always {
            step([$class: 'Publisher', reportFilenamePattern: '**/testng-results.xml'])
        }
    }
}