FROM openjdk:12
VOLUME /tmp
EXPOSE 8002
ADD ./target/ms-item-server-0.0.1-SNAPSHOT.jar ms-item-server.jar
ENTRYPOINT ["java","-jar","/ms-item-server.jar"]