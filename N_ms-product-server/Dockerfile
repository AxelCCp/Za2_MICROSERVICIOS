FROM openjdk:12
VOLUME /tmp
ADD ./target/ms-products-server-0.0.1-SNAPSHOT.jar ms-products-server.jar
ENTRYPOINT ["java","-jar","/ms-products-server.jar"]