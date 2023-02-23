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
  <img src="https://play-lh.googleusercontent.com/8weIaB75j3eCHkY6DOoeQu-S3c7OMjrPtzFPJvJ-SKitwiOqFa3kOcR54lShxN0ijg=w480-h960-rw" title="JUnit5" alt="JUnit5" width="40" height="40"/>&nbsp;
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
  6. Unlock your Jenkins:
    
    6.1 to get the password, open the terminal of the container "jenkins-docker docker:dind" and run the query:
   
   > cat /var/jenkins_home/secrets/initialAdminPassword
   
    6.2 copy the password and paste it into the field
  
  7. Install suggested plugins
  
  8. Create admin login & password
  
  9.Configure Jenkins:
  
    9.1 Managing Plugins -> Available -> Enter "Allure" (tick) -> Enter "labelled" (tick) -> Install without restart
    
    9.2 Configuration of global tools -> Add Gradle (add name ex."gradle jenkins" and tick install automatically) -> 
    Add Allure Commandline (add name ex."allure jenkins" and tick install automatically) -> Save
    
  10. Create Item -> Create Name of Pipeline -> Ok
  
    10.1 Global -> Add description -> Tick "This is a parameterized build" and choose "String Parametr" -> 
    Add Name: TEST_BRANCH_NAME -> Enter Default value: master -> In the "Pipeline" section, select definition 'Pipeline script
    from SCM' -> In "SCM" select definition 'GIT' -> Enter 'https://github.com/Artdianic94/PracticalTask.git' in "Repository URL" ->
    Enter 'jenkins.groovy' in "Script Path" -> Save
  
  11. Build now

    
