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
			stage("Install a project with a clean slate") {
        	    steps{
            	    dir('mytaskguru'){
                	    sh 'npm install'
                	}
            		}
        	}

			stage('Maven Build'){
            	steps {
                	dir('MyTaskGuru'){

                    	sh 'mvn clean install'

            		}
           		}
		}
		
    }	
	
}
