# Homework for candidates - Simple weather service
Implement a service providing weather conditions in pre-selected locations. The service primarily loads the data from attached JSON file and adds some business logic to it.

# Example 1
## backend server - java + spring
* expose REST endpoint for fetching temperature data for given city - something like /api/weather/cities/:city/temperatures - (/api/weather/cities/ostrava/temperatures)
    * source of weather data is attached JSON file, data represents maximum temperature in celsius for the given city for several days in format "dd-mm-yyyy"
* expose REST endpoint for fetching data for all cities

## Checkpoint 1
Fetch temperature values from /api/weather/cities/ostrava/temperatures.
Fetch cities from /api/weather/cities/.

# Example 2
## backend server - java + spring
* expose REST endpoint for creating new city in cities resource
    * it is OK to keep new cities only in memory, no need to store it in JSON file
  * create a validation to check if city already exists

## Checkpoint 2
Through tool of your choice add new city to /api/weather/cities.
Get the temperatures from "new city" and from not existing city.

# Example 3
## backend server - java + spring
  * expose REST endpoint for adding max temperature values for given city
    * it is OK to keep new temperature values only in memory, no need to store it in JSON file
  * create a validation to check if temperature value already exists

## Checkpoint 3
Through tool of your choice add new temperature values to /api/weather/cities/brno/temperatures.
When submitted temperature values already exists return HTTP error which describes this situation.
Get new temperatures from /api/weather/cities/brno.

---
# Technologies
The list of recommended technologies:
1. Java 8
2. Maven
3. Spring (for Web services, for dependency injection)

# Deliverables
1. Source code – well designed, readable, containing well commented code, JavaDoc provided for at list all the public artefacts (interfaces, classes, methods)

2. Build and deployment instructions
Anyone should be able to launch your service based on them.

# Code Design
You should produce an easily maintainable code. Prefer the code maintainability and readability to “a quick and dirty solution”. The architecture should clearly split at layers:

1. Endpoint - REST Controller
The layer only exposes the service interfaces to the clients of the service. There should be no business logic in this layer. If you are tempted to copy & paste any code here, consider that it may belong to the service layer.
The layer maps the requests to domain objects (business classes), passes them to the service layer. Then it maps the domain objects returned from the service layer to the response. This enables to modify an existing endpoint or to add a new endpoint at any time without affecting other parts of the application.

2. Service
The layer implements the complete business logic, such as supported locations, validation mechanism…

---

# notes for commits
  * have at least 3 commits for Example 1, Example 2 and Example 3
  * if you need more commits, create commits around logical pieces
