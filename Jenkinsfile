pipeline {
    agent any
    stages {
        stage("Hello") {
            steps {
                sh 'svn info --username=doo --password=doo svn://host.docker.internal'
                sh 'pwd'
                sh 'whoami'
            }
        }
        stage("Checkout") {
            steps {
                sh 'echo \'jenkins\' | sudo -kS svn cleanup'
                // sh 'echo \'jenkins\' | sudo -kS svn checkout --username=doo --password=doo svn://host.docker.internal -r1'
                
                script {
                    def response = sh(script: 'curl -X GET "http://host.docker.internal:8080/svn/deployReady"', returnStdout: true)
                    echo "${response}"
                    def json = readJSON text: response
                    echo "${json}"
                    
                    json.each { line ->
                        echo "${line}"
                        echo "${line.revision}"
                        echo "${line.path}"
                        
                        sh "echo \'jenkins\' | sudo -kS svn update ${line.path} -r${line.revision} --depth empty"
                    }
                }
            }
        }
    }
}