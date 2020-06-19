USERAPI
This is an API created using java using spring boot framework,to fetch user details for all the users living in London or around within 50 miles from London.

Prerequisites :
1. JAVA (version 8 or above) should be installed on the system.
2. Maven should be installed
3. IDE (Eclipse / Intelli J) should be installed (in case api should be run using an IDE)

Installation
TO install the project, follow the steps below -
clone / download the git project using git clone and git checkout commands. 

USING AN IDE
1. Import the Project using any IDE (IntelliJ / eclipse). 
2. Open the UserApplication.java and right click and run the class.

USING A JAR
1. The API can also be run using a jar
2. To run the api using jar - browse to the location where the project has been downloaded / cloned 
3. Run the command mvn clean install , which would create the jar - user-info-service-0.0.1-SNAPSHOT under target folder
4 browse to target and run the command java -jar user-info-service-0.0.1-SNAPSHOT.jar , which would start the application and can be tested using a browser


Usage
The API can be used 
1. To fetch the details of all the users who are either living in London or whose current coordinates are around 50 miles from London.
The details can be accessed by uisng URL - http://localhost:8182/London/users

2. To fetch the details of all the users residing in a city , for example pass the city name - London in the url to fetch details of users living in London
The details can be accessed by using URL - http://localhost:8182/city/London/users

3. To fetch the details of all the users using URL - http://localhost:8182/users

As per the requirement, the application is fetching details from https://bpdts-test-app.herokuapp.com
To change the port number (in case 8182 is already used on your machine, kindly change it in application.properties file)

