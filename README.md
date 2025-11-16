# The membank api

This api contains a sample account api based on Spring Boot

Available at:
https://github.com/cknudsensmail-svg/membank.git

The api contains a simplified account service exposed as a restservice:

## create: 
Create an account
## list: 
List all accounts in membank
## transfer: 
Transfer amount between accounts in membank

The membank at current has no actual persistency, so be aware of the fact that you could loose your money as fast as betting on horses.

Health monitoring at current monitors the heap mem using Spring Boot Custom HealthIndicator, see
http://localhost:8080/actuator/health

## Security:
x-auth-token header grants access to the api

account secretToken needed to verify outgoing transfers

## postman collection
Postman collection added, see:
src/postman/membank.postman_collection.json


