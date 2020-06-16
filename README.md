USERAPI
This is an API created using spring boot,to fetch user details for all the users living in London or around within 50 miles from London.

Installation
TO install the project, follow the steps below -
1. clone / download the git project using git clone and git checkout commands. 
2. Import the Project using any IDE (IntelliJ / eclipse). 
3. Open the UserApplication.java and right click and run the class.

Usage
The API can be used 
1. To fetch the details of all the users who are either living in London or whose current coordinates are around 50 miles from London.
The details can be accessed by uisng URL - http://localhost:8182/London/users

2. To fetch the details of all the users residing in a city , for example pass the city name - London in the url to fetch details of users living in London
The details can be accessed by using URL - http://localhost:8182/city/London/users

3. To fetch the details of all the users using URL - http://localhost:8182/users

As per the requirement, the application is fetching details from https://bpdts-test-app.herokuapp.com
To change the port number (in case 8182 is already used on your machine, kindly change it in application.properties file)

