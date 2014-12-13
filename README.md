solutionSolver
==============

Java implementation used to parse and solve a simple formula from a rabbitmq queue and to send the result back to an exchange.

To build the project use the following:
```
mvn clean install
```

To run solutionSolver and connect to rabbitmq on localhost:
```
java -jar target\solutionService-1.0-SNAPSHOT.jar localhost
````
