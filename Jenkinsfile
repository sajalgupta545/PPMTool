pipeline {
    environment {
			DOCKERHUB_REGISTRY = "prateek1o1/MyTaskGuru-Fullstack"
			DOCKERHUB_CREDENTIALS = credentials('docker-jenkins')
    }
    
    agent any 
    
    stages {
        
			stage('Git Pull') {
				steps {
					git url: 'https://github.com/prateek1o1/MyTaskGuru-Fullstack.git',
					branch: 'master'
				}
			}
    }
}
