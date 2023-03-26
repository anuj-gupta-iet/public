First of all we need to start Eureka Discovery service
then other applications can be started with will be discovered by Service Discovery at runtime

### Creating a parent pom project e.g. spring-microservice-architecture
* Create a Maven Project by selecting 'skip archetype selection' checkbox and select packaging as pom

### Creating a spring boot project as a module inside above parent pom project e.g. spring-order-microservice
* Create a Maven Module inside above parent pom
* Create a seperate spring boot starter project by selecting required starters
* open pom.xml of newly created spring boot project and copy its contents inside <project></project> to pom.xml of maven module
* also copy application.yml from src/main/resources

### Creating a Spring Eureka Service Registry Server
* create project spring-eureka-service-registry which as as discovery server
* dashboard url - http://localhost:8761/

### Registering an existing microservice e.g. spring-payment-microservice with Spring Eureka Service Registry Server
* add dependencies in microservice's pom.xml, search by comment <!-- spring eureka service discovery -->
* add eureka configuration in its application.yml and add application name
* add @EnableEurekaClient annotation in its spring boot main class
* now use any service by its name e.g. http://localhost:10002/processPayment -> http://PAYMENT-SERVICE/processPayment

### Creating an API gateway service
* create project spring-api-gateway and configure microservices in its application.yml
* now microservices can be accesses on gateway URL as well e.g. spring-payment-microservice will have gateway URL as http://localhost:9001/payment/processPayment

### Creating a Spring Config Server to provide Common configurations among microservices
* create a git repo and put all common config at that location e.g. https://github.com/anujgupta85/spring-config-server
* create config server project e.g. spring-config-server and configure it with above git repo

### Registering an existing microservice e.g. spring-order-microservice with Spring Config Server
* add dependencies in microservice's pom.xml, search by comment <!-- config server client -->
* create one bootstrap.yml parallel to application.yml and put config server details in it 
* remove all common properties in microservice's application.yml
* refer common configurations present in git repo as per this class SpringOrderMicroserviceApplication.java
* now in order to test a call to order service start services in below sequence
	1. spring-eureka-service-registry
	2. spring-config-server
	3. spring-api-gateway
	4. either spring-order-microservice or spring-payment-microservice

### Creating a Zipkin server (Distributed Tracing using Sleuth and Zipkin)
* download zipkin executable jar from maven repo
* java -jar zipkin-server-2.12.9-exec.jar (F:\jars)
* dashboard http://localhost:9411/zipkin/

### Registering a microservice with Zipkin server 
* add zipkin and sleuth starter dependencies in that microservice, search by comment <!-- zipkin dependencies -->
* add zipkin server configurations in its application.yml
* suppose there is a chain of calls which involves these microservices order-service -> payment-service -> gpay-service, now if call fails at some mid way service e.g. gpay-service then search complete trace of calls using traceid on zipkin dashboard  

