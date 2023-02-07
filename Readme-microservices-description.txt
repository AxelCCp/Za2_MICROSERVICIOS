Hello! :d
Note : My microservices are arranged by development stages, and this file highlights the different technologies used at each stage:
Note : The database is simple, the idea is to show the technologies related to microservices with Spring boot.
Note : To test the microservices, run all the microservices that start with the same letter.
Note : In "*Technologies" the negative sign "-" means that some technology was removed
		In "Technologies" the plus sign "+" means that some technology was added.

------------------------------------------------------------------------------------------------------------------

STAGE "A"
*Note : The microservices can be connected with RestTemplate or FeignClient.
*Technologies:
	+ MySql.
	+ Open Feign. 
	+ Rest Template. 
	+ JPA, Spring web.

------------------------------------------------------------------------------------------------------------------

STAGE "B"
*Note : Version change of spring boot and spring cloud to use ribbon for load balancing.
*Note : Test load balancing on ports 8001 and 9001 with -Dserver.port=9001 -Dserver.port=8001 

*Technologies:

	MySql, Open Feign, Rest Template, JPA, Spring web.
	
	+ Ribbon.

------------------------------------------------------------------------------------------------------------------

STAGE "C"

*Technologies:

	MySql, Open Feign, Rest Template, JPA, Spring web.
	
	- Ribbon.
	+ Eureka server.
	+ Hystrix and ribbon (TimeOut). 
	+ Gateway Zuul (pre and post Filters).

------------------------------------------------------------------------------------------------------------------

STAGE "D"

*Technologies:

	MySql, Open Feign, Rest Template, JPA, Spring web, Eureka server, Hystrix and ribbon (TimeOut). 
	
	+ Spring cloud gateway (Global Filter, Filter Factory).
	
------------------------------------------------------------------------------------------------------------------

STAGE "E"

*Technologies:
	
	MySql, Open Feign, Rest Template, JPA, Spring web, Eureka server, Hystrix and ribbon (TimeOut),
	Spring cloud gateway (Global Filter, Filter Factory).
	
	- Hystrix (Hystrix is removed in ms-item-server)
	+ Resilience4J (Resilience4J is added in ms-item-server).
	+ Spring Cloud Bootstrap (Bootstrap is added in ms-item-server).
	
	******************
	ms-item-server:
		*With "class AppCircuitBreakerConfig".
		*With "Application.yml".
		*Also see the RestController.
	******************
	
	TEST: 
	
	test slow call : localhost:8002/id/7/q/4
	Test the Circuit Breaker using config class AppCircuitBreakerConfig :
		Call with error --> localhost:8002/id/10/q/4   localhost:8002/idx/10/qx/4       
		Call ok --> localhost:8002/id/3/q/4            localhost:8002/idx/7/qx/4
		
	
	Test the Circuit Breaker using Application.yml : 
		
		localhost:8002/idy/7/qy/4
		localhost:8002/idy/10/qy/4

	**************************************************
	
	Ms-gateway-server with Resilience4J
	
	With Application.yml
	
		Test: localhost:8091/g/prod/7
	

------------------------------------------------------------------------------------------------------------------


STAGE "F"

*Technologies:

	MySql, Open Feign, Rest Template, JPA, Spring web, Eureka server, Hystrix and ribbon (TimeOut),
	Spring cloud gateway (Global Filter, Filter Factory), Resilience4J, Spring Cloud Bootstrap.
	
	+ SPRING CLOUD CONFIG SERVER.
	+ ACTUATOR


*Check the configuration: 
	
	localhost:8888/ms-item-server/default
	
	localhost:8888/ms-item-server/dev
	
	localhost:8888/ms-item-server/prod
	
	localhost:8007/get-config
	
	localhost:8005/get-config
	
	

*ACTUATOR:
localhost:8007/actuator/refresh
	

------------------------------------------------------------------------------------------------------------------
	
STAGE "G"

*Technologies:

	MySql, Open Feign, Rest Template, JPA, Spring web, Eureka server, Hystrix and ribbon (TimeOut),
	Spring cloud gateway (Global Filter, Filter Factory), Resilience4J, Spring Cloud Bootstrap,
	SPRING CLOUD CONFIG SERVER, ACTUATOR, GIT & GITHUB. 
	
	
	
	*Development of the complete CRUD between microservices Item and Products. It can be used with RestTemplate or Feign. You only need change the @Qualifier in the RestController of Item microservice.
	
	
------------------------------------------------------------------------------------------------------------------

STAGE "H"

Project Microservice commons. Generation of jar with maven.

*Technologies:

	Open Feign, Rest Template, JPA, Spring web, Eureka server, Hystrix and ribbon (TimeOut),
	Spring cloud gateway (Global Filter, Filter Factory), Resilience4J, Spring Cloud Bootstrap,
	SPRING CLOUD CONFIG SERVER, ACTUATOR, GIT & GITHUB. 

	- mySql
	+ H2


	+ Library commons microservice development.
	
	
------------------------------------------------------------------------------------------------------------------


STAGE i:
	
	STAGE i1:
	
		I create a new microservice "ms-users-server". It is for the Security.
		
		*Technologies:
		
			MySql, Open Feign, Rest Template, JPA, Spring web, Eureka server, Hystrix and ribbon (TimeOut),
			Spring cloud gateway (Global Filter, Filter Factory), Resilience4J, Spring Cloud Bootstrap,
			SPRING CLOUD CONFIG SERVER, ACTUATOR, GIT & GITHUB. 
		
			+ Data rest.
			
		*url :
			With zuul server:
			localhost:8090/z/users/userdao/search/search-username?username=axelccp
			
			With spring cloud gateway:
			localhost:8091/g/users/userdao/search/search-username?username=axelccp
			
	---------------------------------------------------------------------
			
	STAGE i2:
		
		*Creation of new Microservice:
		
			Z2_ms-commons-users-server : develop of a new library project. Jar generation (mvnw.cmd install).
		
		*Modification of microservice:
		
			i2_ms-commons-users-server : modification for to work with Z2_ms-commons-users-server.
		
		*Creation of new Microservice:
		
			ms-oauth2-server : implementing security to microservices.
			
		*Zuul;
			ms-oauth2.server now is registered in Zuul.
			
		*Testing with POSTMAN :
		
			Use "POST".
			localhost:8090/z/security/oauth/token
		
			Token generation: 
				
				In "authorization" : 
					Username : angularfrontwebapp
					Password : 1234567
					
				In "body/x-www-form-urlencoded" :
					Username : axelccp
					Password : 1234567
					Grand_type : password
			
	
	Note : the accesses are still with permit all.

	---------------------------------------------------------------------
			
	STAGE i2:
	
	*Technologies:
	
		H2, MySql, Open Feign, Rest Template, JPA, Spring web, Eureka server, Hystrix and ribbon (TimeOut),
		Spring cloud gateway (Global Filter, Filter Factory), Resilience4J, Spring Cloud Bootstrap,
		SPRING CLOUD CONFIG SERVER, ACTUATOR, GIT & GITHUB, Data rest, 
		
		+ Oauth2.
		+ Zuul like a resource server with oauth2.
		+ Timeout with hystrix (zuul).
	
		----------
		
		*Creation of new Microservice:
		
			Z2_ms-commons-users-server : develop of a new library project. Jar generation (mvnw.cmd install).
		
		*Modification of microservice:
		
			i2_ms-commons-users-server : modification for to work with Z2_ms-commons-users-server.
		
		*Creation of new Microservice:
		
			ms-oauth2-server : implementing security to microservices.
			
		*Zuul
			
			ms-oauth2.server now is registered in Zuul.
			
		----------
			
		*POSTMAN :
		
			Use "POST".
			localhost:8090/z/security/oauth/token
		
			Token generation: 
				
				In "authorization" : 
					Username : angularfrontwebapp
					Password : 1234567
					
				In "body/x-www-form-urlencoded" :
					Username : axelccp
					Password : 1234567
					Grand_type : password
			
	
		Note : the accesses are still with permit all.
		
		----------
	
		*ms-springcloudconfig-server : configuration oauth2 "application.properties" on github.
	
		*ms-zuul-server : with spring cloud config.
		
		*ms-oauth2-server : with spring cloud config.
		----------
		
		
	---------------------------------------------------------------------


	STAGE i3:


		*Technologies:

		H2, MySql, Open Feign, Rest Template, JPA, Spring web, Eureka server, Hystrix and ribbon (TimeOut),
		Spring cloud gateway (Global Filter, Filter Factory), Resilience4J, Spring Cloud Bootstrap,
		SPRING CLOUD CONFIG SERVER, ACTUATOR, GIT & GITHUB, Data rest, commons, Oauth2,
		Zuul like a resource server with oauth2, Timeout with hystrix (zuul).
	
		+CORS (of spring security oauth2, to connect a frontend that can be on another server).


		*handling of login attempts by the user in ms-oauth2-server.


------------------------------------------------------------------------------------------------------------------



STAGE "J"

GATEWAY SECURITY CONFIGURATION 

*Technologies:

H2, MySql, Open Feign, Rest Template, JPA, Spring web, Eureka server, Hystrix and ribbon (TimeOut),
Spring cloud gateway (Global Filter, Filter Factory), Resilience4J, Spring Cloud Bootstrap,
SPRING CLOUD CONFIG SERVER, ACTUATOR, GIT & GITHUB, Data rest, commons, Oauth2,
Zuul like a resource server with oauth2, Timeout with hystrix (zuul).

+ Spring cloud gateway security.
+ Webflux.
+ jwt



	

	
	
	


	
	
