# PracticalTask

A project to automate the testing of the Amazon web application. There are 4 test cases implemented in this project. The project contains a dockerfile that allows you to run Jenkins CI locally in a docker container and a configuration file for running implemented tests using Declarative Pipeline. The project has set up test reporting via Allure.

## Covered functionality
> UI Tests
1. Successful authorization
2. Search products 
3. Adding the product into the cart from product’s page
4. Added product is displayed in the cart
## Technology stack
### :hammer_and_wrench: Languages and Tools :
<div>
  <img src="https://github.com/devicons/devicon/blob/master/icons/java/java-original-wordmark.svg" title="Java" alt="Java" width="40" height="40"/>&nbsp;
  <img src="https://github.com/devicons/devicon/blob/master/icons/gradle/gradle-plain.svg" title="Gradle" alt="Gradle" width="40" height="40"/>&nbsp;
  <img src="https://github.com/devicons/devicon/blob/master/icons/selenium/selenium-original.svg" title="Selenium" alt="Selenium" width="40" height="40"/>&nbsp;
  <img src="https://github.com/devicons/devicon/blob/master/icons/docker/docker-original-wordmark.svg" title="Docker" alt="Docker" width="40" height="40"/>&nbsp;
  <img src="https://github.com/devicons/devicon/blob/master/icons/jenkins/jenkins-original.svg" title="Jenkins" alt="Jenkins" width="40" height="40"/>&nbsp
</div>

+ In this project, autotests are written in Java using Selenium for UI tests.
+ JUnit 5 is used as a library for unit testing.
+ Gradle is used for automated project assembly.
+ Allure Report generates a report on the launch of tests.
+ Jenkins CI runs locally in the docker container, which runs tests.
## Running tests in Jenkins, which is installed in a Docker container
#### Docker application should be installed on your local machine
1. Create a bridge network in Docker using the following docker network create command:

>docker network create jenkins

2. In order to execute Docker commands inside Jenkins nodes, download and run the docker:dind Docker image using the following docker run command:

> docker run \
  --name jenkins-docker \
  --rm \
  --detach \
  --privileged \
  --network jenkins \
  --network-alias docker \
  --env DOCKER_TLS_CERTDIR=/certs \
  --volume jenkins-docker-certs:/certs/client \
  --volume jenkins-data:/var/jenkins_home \
  --publish 2376:2376 \
  docker:dind \
  --storage-driver overlay2
  
3. Build a docker image from Dockerfile in this project and assign the image a meaningful name, e.g. "myjenkins-blueocean:2.375.3-1":

> docker build -t myjenkins-blueocean:2.375.3-1 . 

4. Run myjenkins-blueocean:2.375.3-1 image as a container in Docker using the following docker run command:

> docker run \
  --name jenkins-blueocean \
  --restart=on-failure \
  --detach \
  --network jenkins \
  --env DOCKER_HOST=tcp://docker:2376 \
  --env DOCKER_CERT_PATH=/certs/client \
  --env DOCKER_TLS_VERIFY=1 \
  --publish 8080:8080 \
  --publish 50000:50000 \
  --volume jenkins-data:/var/jenkins_home \
  --volume jenkins-docker-certs:/certs/client:ro \
  myjenkins-blueocean:2.375.3-1 
  
  5. Open http://localhost:8080
  6. Configure pipeline in Jenkins (Git Repository, Script Path 'jenkins.groovy')
