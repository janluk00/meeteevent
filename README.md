# Meeteevent
An application that enables efficient management of events. It has a production version (master branch), which in the past was deployed using the Heroku platform.
## Description
### Authentication
* Authentication is done through JWT tokens.
### Event Management
* **Creating events:** Users can create a new event with the necessary information.
* **Assigning tags:** Users can assign predefined tags to an event.
* **Information about events:** Users can check events in which the user participates, which he owns and those that have ended.
### Event recommendation algorithm
* The algorithm sorts the events, starting with those that might be the best fit for the user. It works by using tags from events the user attends and those he doesn't.
### Technologies used in the project
* Java 17
* Spring Boot 3.1.6
* PostgreSQL on AWS
* Hibernate
* MapStruct
* Testcontainers
* JUnit 5
* Mockito
* AssertJ
* Gradle
* Docker
* Heroku
## Installation
1. Clone the repo
```
git clone https://github.com/janluk00/meeteevent.git
```
2. Change the branch to dev
```
git checkout dev
```
3. Change directory to the project
```
cd meeteevent
```
4. Run the database
```
docker compose up
```
5. Run the application
- Windows
  ```
  gradlew bootRun
  ```
- Linux
  ```
  ./gradlew bootRun
  ```
## Endpoints
![swagger](https://github.com/janluk00/meeteevent/assets/87024025/173a5ff8-db53-4982-87b3-e62bf157b67d)
