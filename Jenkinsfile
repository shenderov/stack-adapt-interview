pipeline {
    options {
        buildDiscarder(logRotator(numToKeepStr: '20'))
        disableConcurrentBuilds()
    }

    environment {
        EXEC_CMD = "./gradlew clean test -PdriverType=${params.BROWSER} "
        INFLUX_TOKEN = credentials('INFLUX_TOKEN')
    }

    parameters {
        string(name: "BRANCH", description: "Branch to build from", defaultValue: "master")
        string(name: "BASE_URL", description: "Override default base URL", defaultValue: "")
        string(name: "SUITE_FILE", description: "Specify TestNG Suite XML file under src/test/resources/suites/", defaultValue: "")
        choice(name: 'BROWSER', description: "NOTE: In order to run tests, you need an agent with browser installed", choices: 'headless\nchrome\nfirefox\nie\nedge')
        booleanParam(name: 'ENABLE_INFLUX', description: "Enable sending test results to InfluxDB", defaultValue: true)
        string(name: "INFLUX_URL", description: "Specify URL to InfluxDB", defaultValue: "https://us-east-1-1.aws.cloud2.influxdata.com")
        string(name: "INFLUX_BUCKET", description: "Specify InfluxDB bucket", defaultValue: "jenkins")
        string(name: "INFLUX_ORG", description: "Specify InfluxDB org", defaultValue: "selenium")
    }

    agent any

    stages{

        stage('Checkout') {
            steps {
                script {
                    echo "Checking out the project"
                }
                checkout([$class: 'GitSCM', branches: [[name: "${params.BRANCH}"]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/shenderov/stack-adapt-interview.git']]])
            }
        }

        stage ('Test') {
            steps {
                script {
                    if("${params.BASE_URL}" != ""){
                        EXEC_CMD += "-PbaseUrl="${params.BASE_URL}" "
                    }
                    if("${params.SUITE_FILE}" != ""){
                        EXEC_CMD += "-PsuiteFile="${params.SUITE_FILE}""
                    }
                    if("${params.ENABLE_INFLUX}"){
                        EXEC_CMD += " -PenableInfluxDB="true" -PinfluxUrl="${params.INFLUX_URL}" -PinfluxBucket="${params.INFLUX_BUCKET}" -PinfluxOrg="${params.INFLUX_ORG}" "
                        withCredentials([string(credentialsId: "INFLUX_TOKEN", variable: 'secretText')]) {
                            EXEC_CMD += "-PinfluxToken=${secretText}"
                        }
                    }
                    echo "Start test running..."
                    sh "${EXEC_CMD}"
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