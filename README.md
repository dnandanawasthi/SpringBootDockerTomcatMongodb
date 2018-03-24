http://localhost:32798/swagger-ui.html#/
http://www.javainterviewpoint.com/springfox-swagger-2-spring-restful-web-services/

https://avaldes.com/inserting-and-retrieving-binary-data-with-mongodb-using-jax-rs-restful-web-service/#

# springBootDockerTomcatMongodb
"Note this project's few methods are not working.. it will be fixed soon"
docker build -t myproject .

docker-compose -up

Deploy Your Spring Boot WAR to a remote Tomcat Server running in a Docker Container and Container Linked to MongoDB

The demo application uses a REST Controller to respond to mapped URL requests and stores its visitor page count via logging to MongoDB whose value can then be retrieved via another URL mapping. The project concentrates on deploying the application on Docker Containers and not necessarily the application code; this is the primary goal of the Tutorial. 

Covers how to perform container linking via a docker compose YAML file and the docker compose commands (Version 1). Docker Compose will start 3 Containers; Tomcat, MongoDB and Firefox at the correct IP address, port and address bar URL so that you don’t even have to start your browser or enter anything!

All you need to do once you git clone this repo is execute "docker-compose up" from the root project directory to have the environment
re-produced on your docker host.

Follow along by watching my YouTube Tutorial which goes through the entire process here ...

"Deploy Spring Boot to Tomcat MongoDB Docker Containers"

https://youtu.be/XojJV0A3cBw

****************************

        <PORT>  will    be  different, check is by using below command

        docker ps -a

****************************
http://localhost:32771/portal/v1/incidents
Method is POST
content-type:application/json

{
    "id" : "00002",
    "problemDescription": "FM is not monitoring",
    "severity" : "Major",
    "status" : "Open",
    "component" : "NMH",
    "createdBy" : "taotl Poe",
    "assignedTo" : "Cornel Bruse",
    "createDate" : "2012-05-18T05:10:00.0001Z",
    "closeDate" : "2012-05-19T08:10:00.0001Z",
    "lastUpdatedDate" : "2012-05-18T14:00:00.0001Z",
    "solutionDescription" : "Need to be filled ",
    "daysOpen" : 1
}

Methid is GET
http://localhost:32775/portal/v1/incidents/

Methid is GET by Id
http://localhost:32770/portal/v1/incidents/00002
