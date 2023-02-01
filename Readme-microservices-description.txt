Hello! :d
Note : My microservices are arranged by development stages, and this file highlights the different technologies used at each stage:
Note : The database is simple, the idea is to show the technologies related to microservices with Spring boot.
Note : To test the microservices, run all the microservices that start with the same letter.

------------------------------------------------------------------------------------------------------------------

STAGE "A"
*Note : The microservices can be connected with RestTemplate or FeignClient.
*Technologies:
	MySq, Open Feign, Rest Template, JPA, Spring web.

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
	- Ribbon
	+ Eureka server.
	+ Hystrix and ribbon (TimeOut). 
	+ Gateway Zuul (pre and post Filters).

------------------------------------------------------------------------------------------------------------------

STAGE "D"

*Technologies:

	MySql, Open Feign, Rest Template, JPA, Spring web, Eureka server, Hystrix and ribbon (TimeOut). 
	+ Spring cloud gateway(Global Filter, Filter Factory).
 
