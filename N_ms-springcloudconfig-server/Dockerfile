FROM openjdk:12
VOLUME /tmp
EXPOSE 8888
ADD ./target/ms-springcloudconfig-server-0.0.1-SNAPSHOT.jar ms-springcloudconfig-server.jar
ENTRYPOINT ["java","-jar","/ms-springcloudconfig-server.jar"]