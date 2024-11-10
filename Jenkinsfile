pipeline {
    agent any
    stages {
        stage("Hello") {
            steps {
                sh 'svn info --username=doo --password=doo svn://host.docker.internal'
                sh 'ls -al'
                sh 'pwd'
                sh 'whoami'
            }
        }
        /* stage("DeployReady") {
            steps {
                sh 'curl -X GET "http://host.docker.internal:8080/svn/deployReady"'
                sh 'curl -X GET "http://host.docker.internal:8080/svn/deployReady" > file1.txt'
                sh 'cat file1.txt'
            }
        } */
        stage("Checkout") {
            steps {
                sh 'echo \'jenkins\' | sudo -kS svn cleanup'
                // sh 'echo \'jenkins\' | sudo -kS svn checkout --username=doo --password=doo svn://host.docker.internal -r1'
                
                /* script {
                    def changedFiles = 'file1.txt'
                    
                    if (!fileExists(changedFiles)) {
                        error "File ${changedFiles} not found!"
                    }
                    
                    def txt = readFile(changedFiles).readLines()[0]
                    echo "${txt}"
                    def lines = txt.split(',')
                    lines.each { line ->
                        echo "${line}"
                        def parts = line.split(' ')
                        def revision = parts[0]
                        def path = parts[1]
                        echo "${revision}"
                        echo "${path}"
                        
                        sh "echo \'jenkins\' | sudo -kS svn update ${path} -r${revision} --depth empty"
                    }
                } */
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