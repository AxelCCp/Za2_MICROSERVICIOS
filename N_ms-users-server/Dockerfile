FROM openjdk:12
VOLUME /tmp
ADD ./target/ms-users-server-0.0.1-SNAPSHOT.jar ms-users-server.jar
ENTRYPOINT ["java","-jar","/ms-users-server.jar"]