pipeline {
	agent any
	
	stages {

		stage('Git Pull') {
			steps {
				git url: 'https://github.com/sajalgupta545/PPMTool.git',
					branch: 'master'
            }
        }

		// stage("Install a project with a clean slate") {
		// 	steps {
		// 		dir('mytaskguru') {
		// 			sh 'npm install'
  //               }
  //           }
  //       }

		stage('Maven Build') {
			steps {
				dir('MyTaskGuru') {

					sh 'mvn clean install'
                }
            }
        }

		stage('Build MyTaskGuru Frontend Docker Image') {
			steps {
				dir('mytaskguru') {
					sh 'docker build -t sajalgupta545/mytaskguru-frontend:latest .'
                }
            }
        }

		stage('Building MyTaskGuru Backend Image') {
			steps {
				dir('MyTaskGuru') {
					sh 'docker build -t sajalgupta545/mytaskguru-backend:latest .'
                }
            }
        }		
			stage('Pushing MyTaskGuru Frontend Image to DockerHub') {
				steps {
					script {
						withDockerRegistry([credentialsId: "docker-jenkins", url: ""
                    ]) {
							sh 'docker push sajalgupta545/mytaskguru-frontend:latest'
                    }
                }
            }
        }

			stage('Pushing MyTaskGuru Backend Image to DockerHub') {
				steps {
					script {
						withDockerRegistry([credentialsId: "docker-jenkins", url: ""
                    ]) {
							sh 'docker push sajalgupta545/mytaskguru-backend:latest'
                    }
                }
            }
        }

			stage('Removing Docker Images from Local') {
				steps {
					sh "docker rmi sajalgupta545/mytaskguru-frontend:latest"
					sh "docker rmi sajalgupta545/mytaskguru-backend:latest"
            }
        }
		stage('Ansible Deploy') {
             steps {
                  ansiblePlaybook colorized: true, disableHostKeyChecking: true, installation: 'Ansible', inventory: 'inventory', playbook: 'ansible-playbook.yml'
             }
        }
    }
}
