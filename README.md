## Tech Stack

  * Java 8
  * Spring Boot 2.3.3
  * JUnit 5
  * Lombok
  * Swagger 2.7.0 
  * Binary Search Tree algorithm

## Running the application

To build application: 

```bash
mvnw clean install
```
To run application: 

```bash
java -jar target/chargingStation-API-0.0.1-SNAPSHOT.jar
```
To start application: 

```bash
http://localhost:9091/
```
## Testing

```bash
mvnw clean test
```
## Quick Test

Create session

```curl -X POST localhost:9091/chargingSession  
example response
{"id":"bda6acd8-aa5f-463f-a9de-cff8d3e226e2"}
```
Stop session (id from above call)

```
curl -X PUT localhost:9091/chargingSession/bda6acd8-aa5f-463f-a9de-cff8d3e226e2
```
Get summary

```
curl -X GET localhost:9091/chargingSessions/summary
example response
{"totalCount":0,"startedCount":0,"stoppedCount":1}
```
Get all sessions

```
curl -X GET localhost:9091/chargingSessions
example response
[{
    "id": "string",
    "stationId": "string",
    "status": "FINISHED",
    "updatedAt": "2020-09-06T20:36:35.434Z"
  }]
```

## API Documentation

Swagger2 documentation is available.

```
http://localhost:9091/swagger-ui.html#
```

## Java Documentation

```
chargingStation-API/doc/overview-summary.html
```

## Improvements to make
```
- Making thread-safe at field level.
- Some more test cases for BST.
```
