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


------------------------------------------------------------------------------------------------------------------



STAGE "K"


*Technologies:

H2, MySql, Open Feign, Rest Template, JPA, Spring web, Eureka server, Hystrix and ribbon (TimeOut),
Spring cloud gateway (Global Filter, Filter Factory), Resilience4J, Spring Cloud Bootstrap,
SPRING CLOUD CONFIG SERVER, ACTUATOR, GIT & GITHUB, Data rest, commons, Oauth2,
Zuul like a resource server with oauth2, Timeout with hystrix (zuul), Spring cloud gateway security.
Webflux, jwt.

Ms-products.server : with mysql. Now is a client of spring cloud config and the application.properties is in github.


------------------------------------------------------------------------------------------------------------------



STAGE "L"


*Technologies:

H2, MySql, Open Feign, Rest Template, JPA, Spring web, Eureka server, Hystrix and ribbon (TimeOut),
Spring cloud gateway (Global Filter, Filter Factory), Resilience4J, Spring Cloud Bootstrap,
SPRING CLOUD CONFIG SERVER, ACTUATOR, GIT & GITHUB, Data rest, commons, Oauth2,
Zuul like a resource server with oauth2, Timeout with hystrix (zuul), Spring cloud gateway security.
Webflux, jwt.

+ postgreSQL

Ms-users-server : with postgreSql Now is a client of spring cloud config and the properties file is in github.
	

------------------------------------------------------------------------------------------------------------------



STAGE "M"

*Technologies:

H2, MySql, Open Feign, Rest Template, JPA, Spring web, Eureka server, Hystrix and ribbon (TimeOut),
Spring cloud gateway (Global Filter, Filter Factory), Resilience4J, Spring Cloud Bootstrap,
SPRING CLOUD CONFIG SERVER, ACTUATOR, GIT & GITHUB, Data rest, commons, Oauth2,
Zuul like a resource server with oauth2, Timeout with hystrix (zuul), Spring cloud gateway security.
Webflux, jwt, postgreSQL.


	+ SLEUTH
	
	
	+ ZIPKIN   
		
		Application.properties configurations
		Application of @Autowired private Tracer tracer;
		java -jar zipkin.jar
	
	
	+RabbitMQ 
		
		Erlang installation.
		RabbitMQ Installation.
		Environment Variables configuration.
		Installation of RabbitMQ plugins with … C:\WINDOWS\system32>rabbitmq-plugins enable rabbitmq_management
		
		Reset de RabbitMQ  in service and go to : http://localhost:15672
		User : guest
		Password: guest
	
	--------------
	
		RABBITMQ PORTS : 
			CONSOLE BROWSER : 15672
			SERVER : 5672 
	
	--------------
		
	Connecting zipkin with rabbit for connect the microservices with rabbit:
	
		zipkin.cmd file configuration. It is a script file for the configuration of Environment Variable. It connect zipkin with rabbit.
		
		Run zipkin : 
			C:\Users>cd C:\developjava
			C:\developjava>zipkin.cmd
	
	--------------
	
	
	Connecting the MySql Storage with zipkin:
	
		https://github.com/openzipkin/zipkin/
		https://github.com/openzipkin/zipkin/tree/master/zipkin-server
		
		
		On zipkin.cmd file I create a new user of mysql "zipkin":
		
			Environment Variables configuration : 
				STORAGE_TYPE=mysql
				MYSQL_USER=zipkin
				MYSQL_PASS=zipkin
	
		Now, I create a new database "zipkin" in workbench.
		
			Name=zipkin
			Charset=utf8
			Collation=utf8_bin
		
		After, create a new account in users and privileges.
		
		
		Now, create a table with an other zipkin script:
		
			Copy paste :
		
			https://github.com/openzipkin/zipkin/blob/master/zipkin-storage/mysql-v1/src/main/resources/mysql.sql
			
		
		Testing  review the traces with postman and the zipkin database.
		
				

				my zipkin.cmd
				***************************************************
				@echo off
				set RABBIT_ADDRESSES=localhost:5672
				set STORAGE_TYPE=mysql
				set MYSQL_USER=zipkin
				set MYSQL_PASS=zipkin
				java -jar ./zipkin-server-2.24.0-exec.jar
				***************************************************



-----------------------------------------------------------------------------------------------------------------
DOCKER IMAGES AND CONTAINER ....  
-----------------------------------------------------------------------------------------------------------------

ULTIMA SECCIÓN DE DOCKER :
 

Docker --version


145 y 146

	1.-GENERANDO JAR DE MS-SPRING CLOUD CONFIG -SERVER
	
		C:\Users\Fanta\Documents\Za2_MICROSERVICIOS\M_ms-springcloudconfig-server>mvnw clean package
	
	2.-CREA DOCKER FILE EN LA RAIZ DEL PROYECTO, EN ECLIPSE y CONFIGURALO.
	
	3.-CREAR IMAGEN DOCKER EN LA CMD.
	
		Docker build -t ms-springcloudconfig-server:v1 .
		
	4.-VERIFICA CREACION DE IMAGEN
	
		C:\Users\Fanta\Documents\Za2_MICROSERVICIOS\N_ms-springcloudconfig-server>docker images
		
	5.-CREAR LA NETWORK DONDE SE VAN A COMUNICAR TODOS LOS  CONTENEDORES CON LOS MICROSERVICIOS:
	
		C:\Users\Fanta\Documents\Za2_MICROSERVICIOS\N_ms-springcloudconfig-server>docker network create springcloudNetwork
		
	6.-AHORA SE PUEDE EJECUTAR LA IMAGEN PARA CREAR CONTENEDOR:
		
		C:\Users\Fanta\Documents\Za2_MICROSERVICIOS\N_ms-springcloudconfig-server>
		docker run -p 8888:8888 --name ms-springcloudconfig-server --network springcloudNetwork ms-springcloudconfig-server:v1
		
	7.-VER CONTENEDOR DE DOCKER EN RUN:
	
		Docker container ls
		Docker container ps
	

147

	1.-IMAGEN DE EUREKA
	
		C:\Users\Fanta\Documents\Za2_MICROSERVICIOS\N_ms-eureka-server>docker build -t ms-eureka-server:v1 .
		
	2.-IMÁGENES CREADAS
	
		C:\Users\Fanta\Documents\Za2_MICROSERVICIOS\N_ms-eureka-server>docker images
		REPOSITORY                    TAG       IMAGE ID       CREATED         SIZE
		ms-eureka-server              v1        34fb0d11a65d   8 seconds ago   518MB
		ms-springcloudconfig-server   v1        e7e728faafa3   10 hours ago    502MB
		
	
	3.-CREAR CONTENEDOR EUREKA 
	
		C:\Users\Fanta\Documents\Za2_MICROSERVICIOS\N_ms-eureka-server>docker run -p 8761:8761 --name ms-eureka-server --network springcloudNetwork ms-eureka-server:v1
	
		
148
IMAGEN DE MYSQL


	1.-DESCARGA LA IMAGEN DESDE DOCKER HUB Y BUSCA EL COMANDO QUE INDICA. EN ESTE CASO 
	
		docker pull mysql
		
	2.-Y EN LA CMD PONE EL COMANDO MÁS LA VERSION:
	
		docker pull mysql:8
		
	3.-VERIFICA CON:
	
		
		C:\Users\Fanta>docker images
		REPOSITORY                    TAG       IMAGE ID       CREATED          SIZE
		ms-eureka-server              v1        34fb0d11a65d   20 minutes ago   518MB
		ms-springcloudconfig-server   v1        e7e728faafa3   10 hours ago     502MB
		mysql                         8         05b458cc32b9   7 days ago       517MB
		
		
	4.-CREAR UN CONTENEDOR MYSQL CON DOCKER. ESTE SE PUEDE LEVANTAR EN EL PUERTO POR DEFECTO 3306 U OTRO. SI SE USA EL 3306, HAY Q BAJAR EL SERVICIO DE DOCKER PARA Q NO CHOQUEN:
	
		
		-e : para asignar y settear variables de entorno.
		MYSQL_ROOT_PASSWORD=sasa : password de la base de datos.
		MYSQL_DATABASE=ms_product : nombre de base de datos.
		-d : para que levante en background, o sea sin tomar la consola. 
		
		docker run -p 3306:3306 --name ms-mysql-8 --network springcloudNetwork -e MYSQL_ROOT_PASSWORD=sasa -e MYSQL_DATABASE=ms_product -d mysql:8
		
	5.-VERIFICA CONTENEDOR EN RUN : 
	
		C:\Users\Fanta>docker ps
		CONTAINER ID   IMAGE     COMMAND                  CREATED          STATUS         PORTS                               NAMES
		a0b6e0d982cc   mysql:8   "docker-entrypoint.s…"   10 seconds ago   Up 9 seconds   0.0.0.0:3306->3306/tcp, 33060/tcp   C_ms-mysql-8
		
	6.-VERIFICA EN WORKBENCH:
	
		A-HACETE UNA NUEVA CONEXIÓN  EN SETUP NEW CONNECTION" CUANDO SE ABRE LA WEA.
		
		B-ENTRA Y VE SI TE CREO LA BBDD ms_product.
		
		
		
149
DESCARGA DE IMAGEN POSTGRE Y LEVANTAR INSTANCIA:

	1.-DESCARGA
	
		C:\Users\Fanta>docker pull postgres:12-alpine
		12-alpine: Pulling from library/postgres
		8921db27df28: Pull complete
		eb286326f602: Pull complete
		63139c77dd7e: Pull complete
		1ff6c16a0b8e: Pull complete
		8a2859f310af: Pull complete
		d1d66cd04ce3: Pull complete
		460680990628: Pull complete
		9664a190023a: Pull complete
		Digest: sha256:70c633227898bdb6d7b897ead58a3a2e357bbe6f5dd2d0c230263fa0e84d2eca
		Status: Downloaded newer image for postgres:12-alpine
		docker.io/library/postgres:12-alpine
	
	2.-VERIFICA:
	
		C:\Users\Fanta>docker images
		REPOSITORY                    TAG         IMAGE ID       CREATED             SIZE
		ms-eureka-server              v1          34fb0d11a65d   About an hour ago   518MB
		ms-springcloudconfig-server   v1          e7e728faafa3   11 hours ago        502MB
		mysql                         8           05b458cc32b9   7 days ago          517MB
		postgres                      12-alpine   2b840024c973   4 weeks ago         237MB
	
	
	3.-CREA CONTENEDOR Y LEVANTA (BAJA EL SERVICIO DE POSTGRES O CAMBIA EL PUERTO )
		
		
		docker run -p 5432:5432 --name ms-postgres-12 --network springcloudNetwork -e POSTGRES_PASSWORD=sasa -e POSTGRES_DB=ms_users -d postgres:12-alpine
		 
	4.-VERIFICA CONTENEDOR
	
		C:\Users\Fanta>docker ps
		CONTAINER ID   IMAGE                COMMAND                  CREATED          STATUS          PORTS                               NAMES
		076da77fea70   postgres:12-alpine   "docker-entrypoint.s…"   19 seconds ago   Up 17 seconds   0.0.0.0:5432->5432/tcp              C_ms-postgres-12
		a0b6e0d982cc   mysql:8              "docker-entrypoint.s…"   30 minutes ago   Up 30 minutes   0.0.0.0:3306->3306/tcp, 33060/tcp   C_ms-mysql-8
		
	
		
150
EDICION DE LOS APPLICATION PROPERTIES DE GITHUB. 
	
	EL DE PRODUCTOS Y EL DE USUARIOS, EN LA RUTA A LA BASE DE DATOS, EN VEZ DE APUNTAR A LOCALHOST, DEBE APUNTAR AL NOMBRE DEL CONTENEDOR DOCKER DE MYSQL Y POSTGRES.


151

	1.-LOS MICROSERVICIOS AHORA DEBEN CONECTARSE AL CONTENEDOR DE EUREKA Y NO A LA INSTANCIA. SE CAMBIA ESTO EN LOS PROPERTIES DE CADA UNO.

	2.-EN MS ITEM Y ZUUL, VOLVER A ACTIVAR EL TIMEOUT POR SI SE DEMORAN MUCHO LAS PETICIONES CON DOCKER.
	
	3.-EN LOS BOOTSTRAP DE CADA MICROSERVICIO, CAMBIAR EL LOCALHOST POR EL NOMBRE DEL CONTENEDOR DEL MS-SPRINGCLODCONGIG-SERVER.
	

152

	1.-CREA EL DOCKER FILE DE PRODUCTOS
		
	2.-GENERA EL JAR
		
		HAY Q SALTARSE EL TEST DE CONEXIÓN A LA BBDD PA Q NO D'E ERROR.
		C:\Users\Fanta\Documents\Za2_MICROSERVICIOS\N_ms-product-server>mvnw clean package -Dskiptests
		
	3.-CONSTRUYE IMAGEN
	
		docker build -t ms-products-server:v1 .
	
	4.-VERIFICA
	
		C:\Users\Fanta\Documents\Za2_MICROSERVICIOS\N_ms-product-server>docker images
		REPOSITORY                    TAG         IMAGE ID       CREATED          SIZE
		ms-products-server            v1          1714636d234d   12 seconds ago   537MB
		ms-eureka-server              v1          34fb0d11a65d   3 hours ago      518MB
		ms-springcloudconfig-server   v1          e7e728faafa3   13 hours ago     502MB
		mysql                         8           05b458cc32b9   7 days ago       517MB
		postgres                      12-alpine   2b840024c973   4 weeks ago      237MB
		
		
	4.-CREA Y LEVANTA CONTENEDOR:
	
		-P : en mayuscula y sin valor es para puerto random.
	
		docker run -P --name ms-products-server --network springcloudNetwork ms-products-server:v1 
		
	
153
CONTENEDOR ZUUL GATEWAY
	
	0.-docker file y modigica bootstrap

	1.-jar
		mvnw clean package -Dskiptests
		
	2.-i
		docker build -t ms-zuul-server:v1 .
	
	3.-contenedor y run 
		C:\Users\Fanta\Documents\Za2_MICROSERVICIOS\N_ms-zuul-server>docker run -p 8090:8090 --name ms-zuul-server --network springcloudNetwork ms-zuul-server:v1
		
154

	0.-docker file y modigica bootstrap
	
	1.-jar
		 mvnw clean package -Dskiptests
	
	2.-imagen
		docker build -t ms-users-server:v1 .
		
	3-contenedor
		docker run -P --name ms-users-server --network springcloudNetwork ms-users-server:v1
	
	
	 
155
Contenedor oauth

	0.-docker file y modigica bootstrap
	
	1.-jar
		 mvnw clean package -Dskiptests
		
	2.-imagen
		Docker build -t ms-oauth2-server:v1 .
		
	
	3-contenedor
		docker run -p 9100:9100 --name ms-oauth2-server --network springcloudNetwork ms-oauth2-server:v1
	

156
Contenedor item

	0.-docker file y modigica bootstrap y timeout mayor
	
	1.-jar
		 mvnw clean package -Dskiptests
	
	2.-imagen
		docker build -t ms-item-server:v1 .
	
	3-contenedor
	
		docker run -p 8002:8002 -p 8005:8005 -p 8007:8007 --name ms-item-server --network springcloudNetwork ms-item-server:v1
	
		
157
MAS INSTANCIAS DE CONTENEDOR PRODUCTOS CON PUERTO RANDOM.

	docker run -P --name ms-products-server-2 --network springcloudNetwork ms-products-server:v1 
	
158
GENERAR INSTANCIA DE RABBIT MQ

	1.-anda a docker hub y busca la version oficial. Usa la alpine con managenet ya q tiene los plugings:
	
		C:\Users\Fanta>docker pull rabbitmq:3.11-management-alpine
	
	2.-verifica imagen
		
		15672:15672 = puerto de la parte web.
		5672:5672 = puerto del servidor broker de rabbit mq.
		-d : para q levante en background y no se vea.
		
		docker run -p 15672:15672 -p 5672:5672 --name ms-rabbit-mq --network springcloudNetwork -d rabbitmq:3.11-management-alpine
	
	3.-verifica contenedor 
		Docker ps
		Docker ps -a
		
	4.-verifica que funcione
	
		En el browser… localhost:15672
		User : guest
		Password : guest
		


159
ZIPKIN CONFIGURAR BBDD ZIPKIN

	1.-configurar la base de datos para zipkin con mysql.
		1-Entra en la session que se creó anteriormente para zipkin en el workbench. Donde ya está la bbbd de zipkin sin docker.
		2-crea una nueva bbdd.
			Nombre: zipkin
			Charset: utf8
			Collation: utf8_genral_ci
		3-crea un usuario para la nueva bbdd zipkin
			Login name : zipkin
			Password : zipkin
			Authentication type : standard
		4-crear tablas / esquemas:
			1-anda a https://zipkin.io/pages/quickstart.html      ---->      https://github.com/openzipkin/zipkin/
			2-busca zipkin-server
			3-.anda a mysql stogage y aplicar esquema y luego ver esquema      https://github.com/openzipkin/zipkin/blob/master/zipkin-storage/mysql-v1/src/main/resources/mysql.sql
			4.-copia toda la tabla y pegala en el workbench.  Al inicio escribe ----> use zipkin;
		5.-hay q darle permisos al usuario zipkin para usar la bbdd zipkin.
			1-
160
ZIPKIN CONFIGURAR DOCKERFILE ZIPKIN

	1.-METE TU ZIPKIN JAR EN UNA CARPETA.
	2.-COPIA UN DOCKERFILE Y METELO AHÍ TAMBN.
	3.-CONFIGURACION DOCKER FILE:
		FROM openjdk:12
		VOLUME /tmp
		EXPOSE 9411
		ADD ./zipkin-server-2.24.0-exec.jar zipkin-server.jar
		ENTRYPOINT ["java","-jar","/zipkin-server.jar"]
	
	4.-GENERA LA IMAGEN:
	
		C:\developjava\ZIPKIN_SERVER_JAR>docker build -t zipkin-server:v1 .
		
	5.-VERIFICA:
		
		docker images
	
	6.-LEVANTA CONTENEDOR:
	
		Se settean las variables de entorno, las mismas del archivo ziplin.cmd para conectar con rabbit. 
		Tambn se agrega la variable de entorno del contenedor de mysql.
	
		docker run -p 9411:9411 --name zipkin-server --network springcloudNetwork -e RABBIT_ADDRESSES=ms-rabbit-mq:5672 -e STORAGE_TYPE=mysql -e MYSQL_USER=zipkin -e MYSQL_PASS=zipkin -e MYSQL_HOST=ms-mysql-8 zipkin-server:v1
	
	
	7.-VERIFICA CONTENEDOR
	
	8.-VERIFICA REGISTRO DE ZIPKIN EN RABBIT
		localhost:15672
	
	9.-ANDA A zipkin, si carga está bien. Falta configurar los microservicios, para que estos apunten a rabbit y se envien las trazas.
		localhost:9411
	

CLASE 161

	AGREGA ESTA CONFIGURACION EN EL APPLICATION.PROPERTIES DE GITHUB. PARA CONECTAR LOS MICROSERVICIOS CON RABBIT Y ZIPKIN… PARA CONSEGUIR LAS TRAZAS.
	
		spring.zipkin.base-url=http://zipkin-server:9411/
		spring.rabbitmq.host=ms-rabbit-mq
		
		* esto se podría hacer poniendo esta configuracion en cada application.properties de los microservicios, 
		  pero para evitar hacer denuevo las imágenes y los contenedores, se hace así mejor.
		










	

	
	
	


	
	
