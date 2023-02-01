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


*Check the configuration: 
	
	localhost:8888/ms-item-server/default
	

	

	

	
	
	


	
	
