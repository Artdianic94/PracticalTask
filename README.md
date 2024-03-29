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

   
+ In this project, autotests are written in Java using Selenium for UI tests, with JUnit 5 as the unit testing library.
+ Gradle is used as the automated project management tool.
+ The Allure Report generates a report on test launches.
+ This project can be launched in a Docker network that connects containers with Jenkins,
  Selenoid, Selenoid UI, Selenoid/chrome, and Selenoid/firefox.
## Running tests in Jenkins, which is installed in a Docker container
#### Docker application should be installed on your local machine
1. Create a bridge network in Docker using the following docker network create command:

> docker network create jenkins

2. In order to execute Docker commands inside Jenkins nodes, download docker:dind using the following docker command:

> docker pull docker:dind
  
3. Build a docker image from Dockerfile in this project and assign the image a meaningful name, e.g. "myjenkins-blueocean:2.375.3-1":

> docker build -t myjenkins-blueocean:2.375.3-1 . 
  
  4. Next step is to download the Selenoid image, Selenoid-UI image (optionally) and Selenoid images for browsers.You should follow 
  
  the instructions below:
    
     > docker pull aerokube/selenoid:latest
     
     > docker pull aerokube/selenoid-ui:latest
     
     > docker pull selenoid/chrome
     
     > docker pull selenoid/firefox
     
  5. To create a container network, execute the following command in the folder with the Practical Task project:
  
     > docker-compose up -d  
  
  6. Open http://localhost:8090
  
  7. Unlock your Jenkins:
    
    7.1 to get the password, open the terminal of the container "jenkins-docker docker:dind" and run the query:
   
   > cat /var/jenkins_home/secrets/initialAdminPassword
   
    7.2 copy the password and paste it into the field
  
  8. Install suggested plugins
  
  9. Create admin login & password
  
  10. Configure Jenkins:
  
    10.1 Managing Plugins -> Available -> Enter "Allure" (tick) -> Enter "labelled" (tick) -> Install without restart
    
    10.2 Configuration of global tools -> Add Gradle (add name ex."gradle jenkins" and tick install automatically) -> 
    Add Allure Commandline (add name ex."allure jenkins" and tick install automatically) -> Save
    
    10.3 Manage Credentials -> click on System -> Add domain -> enter domain name (e.g. 'amazon') -> create -> 
    adding some credentials -> In "Kind" select definition 'Username with password' -> In "Username" enter your
    amazon email -> In "Password" enter your amazon password -> In "ID" enter 'credentials-id' -> Save
    
  11. Create Item -> Create Name of Pipeline -> Ok
  
    11.1 Global -> Add description -> Tick "This is a parameterized build"-> Add parameter -> choose "String Parameter" -> 
    Add Name: TEST_BRANCH_NAME -> Enter Default value: master -> Add parameter -> choose "String Parameter" -> 
    Add Name: BROWSER -> Add parameter -> choose "String Parameter" -> 
    Add Name: REMOTE_BROWSER -> Add parameter -> choose "String Parameter" -> 
    Add Name: maxParallelForks -> In the "Pipeline" section, select definition
    'Pipeline script from SCM' -> In "SCM" select definition 'GIT' ->
    Enter 'https://github.com/Artdianic94/PracticalTask.git' in "Repository URL" -> In the section
    "Branches to build" field "Branch Specifier (blank for 'any')" enter ${TEST_BRANCH_NAME} -> Enter 'Jenkinsfile' in "Script Path"
    -> Save
  
  12. Build with parameters:
      
      12.1 In the 'TEST_BRANCH_NAME' field, you can enter any branch that exists in the project on GitHub.
      
      12.2 In the 'BROWSER' field, you can enter a browser name. It can be 'chrome', 'firefox', or 'remote'.
      If you choose 'remote', then in the 'REMOTE_BROWSER' field, you can enter a remote browser name. 
      It can be 'chrome' or 'firefox'.
      
      12.3 In the 'maxParallelForks' field, you can enter a number of threads.
      
  13. Build

    
