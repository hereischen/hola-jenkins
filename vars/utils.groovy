def call(){
    properties([
        parameters([
            string(name: 'example_param1', defaultValue: 'hello word')
        ]), 
    ])

    node {
        stage('Checkout') {
            checkout scm
        }
        stage("Package artifact") {
            sh "echo this is it"
            println("properties:${params.example_param1}")
        }

    }
}