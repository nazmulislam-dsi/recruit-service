# Command Line Battleground (cli-bg)

## Objective 
* Need to build a backend service with spring-boot that handles recruiting process (JSON based),
* API job offers and applications from candidates
* Ad-hoc calculation is necessary.
* User has to be able to create a job offer and read a single and list all offers.
* Candidate has to be able to apply for an offer.
* User has to be able to read one and list all applications per offer.
* User has to be able to progress the status of an application.
* User has to be able to track the number of applications.
* User has to be able to status change triggers a notification.
* Status change should triggers a completely different business/notification case.

## Planning and analysis

* Requirement analysis and business understanding 
* Story building and activity listing 
* TDD planning

## Design
* Component design
* API, DTO, Persistence flow
* Swagger Integration.

## Coding Design

* According to requirement this service is pretty straightforward with some CRUD API for offer and application. On top of spring-boot 2.0, JpaRepository is being used to achieve the requirement. 
* To trigger completely different notification for different status, here **strategy pattern** is being used with different **singleton implementation**.


## UML

### Class Diagram
![Class-Diagram](docs/Class-Diagram.png)

### Use-case Diagram 
![Usecase-Diagram](docs/Usecase-Diagram.jpg)

### Dataflow Diagram 
![Dataflow-Diagram](docs/Dataflow.png)


## Scopes

* This application has scopes to create any type of business for different status.
* We can achieve auditing introducing some more fields to entities.

## Requirement

- [x] Need to have [JDK 8](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html) installed and available to $PATH
- [x] You need to have [Maven 3](http://maven.apache.org/install.html) installed and available to $PATH or you can simply use mvnw.


## Instruction to build
 
Project is based on Maven and can be built using the following command

*Clean Before Build:*
```
mvn clean
``` 
*Test Run:*
```
mvn test
``` 
*Package creation:*
```
mvn package
``` 

It will execute all the unit test script and validate the build. The build package will be available to **target** folder. 

## Instruction to run **
 
### IDE

Run the program with `main` method from `org.ni.recruitservice.RecruitServiceApplication` class

### Command-line
```
java -jar target/recruit-service-0.0.1-SNAPSHOT.jar
```
If you are already in **target** folder then just 
```
java -jar recruit-service-0.0.1-SNAPSHOT.jar
```
Or simply type the command bellow -

```
mvn spring-boot:run
```

** You have to have 8080 port free to run this application.
 
After running the application you can access it Swagger UI from with bellow URL

```
http://localhost:8080/
```

## Constrain 
* To get the count of the application under one offer we are querying 2 times. Need to fetch both info at once.
* While creating swagger definition and exception is occurring, as it is not creating any problem and it might be a library issue.

## Documentation
You can use SwaggerUI or postoffice to call the exposed APIs.

Checkout API description with screenshots -

* Create a offer 

>>User can create an offer by simply hit the **POST /offers** API. 

>>Example payload :

```
{
  "startDate": "2018-10-07",
  "title": "Job 02 - SQA Engineer"
}
```

![Offer-POST](docs/screenshots/Offer-POST.png)

* Get offer list

>>User can get the offer list from the **Get /offers** API. 

![Offer-GET](docs/screenshots/Offer-GET.png)

* Get a single offer

>>User can get a single offer from the **Get /offers/{offerId}** API with offerId. 

>>Example 

>>Parameter :
```
offerId : 1
```

![Offer-GET-2](docs/screenshots/Offer-GET-2.png)

* Create an application

>>User can create a application with **POST /offers/{offerId}/applications** API

>>Example 

>>Parameter :
```
offerId : 1
```
>>Payload :
```
{
  "applicationStatus": "APPLIED",
  "candidateEmail": "naim.5221@gmail.com",
  "resume": "I am a Software Engineer."
}
```
![Application-POST](docs/screenshots/Application-POST.png)

* See application list of an offer.

>>User can get application list under a offer with **GET /offers/{offerId}/applications** API

>>Example 

>>Parameter :
```
offerId : 1
```
![Application-GET](docs/screenshots/Application-GET.png)

* See a particular application under an offer.

>>User can get a particular application under a offer with **GET /offers/{offerId}/applications/{applicationId}** API

>>Example 

>>Parameter :
```
offerId : 1
applicationId : 2
```
![Application-GET2](docs/screenshots/Application-GET2.png)

* Update a particular application under an offer.

>>User can get a particular application under a offer with **PATCH /offers/applications/{applicationId}** API

>>Example 

>>Parameter :
```
applicationId : 2
```
>>Payload :
```
{
  "applicationStatus": "HIRED"
}
```

![Offer-PATCH](docs/screenshots/Offer-PATCH.png)

Good luck :+1: