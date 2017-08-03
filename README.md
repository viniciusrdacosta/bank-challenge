[![Build Status](https://travis-ci.org/viniciusrdacosta/bank-challenge.svg?branch=master)](https://travis-ci.org/viniciusrdacosta/bank-challenge) [![Coverage Status](https://coveralls.io/repos/github/viniciusrdacosta/bank-challenge/badge.svg)](https://coveralls.io/github/viniciusrdacosta/bank-challenge) [![codebeat badge](https://codebeat.co/badges/3695afed-a767-4f13-bfc4-16bd332e4370)](https://codebeat.co/projects/github-com-viniciusrdacosta-bank-challenge-master)

## How Run Locally - Developer Mode
 1. Clone repo
 2. Run `./gradlew bootRun`
 3. Api exposed in `http://localhost:8080/transactions` or `http://localhost:8080/statistics`

## How Run Locally - User Mode
 1. Clone repo
 2. Run `./gradlew assemble`
 3. Run `java -jar build/libs/bank-challenge.jar`
 4. Api exposed in `http://localhost:8080/transactions` or `http://localhost:8080/statistics`
 
## How Run Tests
 1. Clone repo
 2. Run `../gradlew check unitTest endToEndTest audit pitest`
 4. Reports published under `build/reports`
 
## Implementation
  ### Language
  1. Java 8
  
  ### Build Tool
  1. Gradle
  
  ### Relevant Frameworks   
  1. Spring-Boot 
      1. spring-boot-starter-web
      2. spring-boot-starter-actuator
      3. spring-boot-dependencies
      4. spring-boot-starter-test
 2. Lombok
 3. Logback
 4. AssertJ 
 5. RestAssured
 
 ### Design Decisions
 1. `CopyOnWriteArrayList` used to store transactions since that it is thread safe
 2. Architecture following `MVC` package structure
 3. Request validation uses custom annotation and `ContraintValidator`
 4. 3 kinds of tests were created: unit, end to end and mutation tests
 5. Assumed that the timestamp base for `/statistics` is the current request date in UTC
 6. All timestamps are in `milliseconds` format, using UTC timezone
 7. Audit task was removed from CI due repository instability
 
 
## To Do: Next steps to improve the codebase
  ### Code Quality
  1. Integration with Coveralls
  2. Performance Tests
  
  ### CI / CD
  1. Create Pipeline
  2. Deploy to PAS
  3. Migrate to CircleCI (Maybe?)
  
  ### Refactoring
  1. Extract error handler from `TransactionController` to a global error handler
  2. Split `TransactionController` in `Transaction` and `Statistics` controllers
  3. Improve error responses, instead of return `204` for bad request, return `400`
  4. Extract some configurations from `build.gradle` to separated file.
  
   
