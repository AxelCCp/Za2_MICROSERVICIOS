FROM openjdk:12
VOLUME /tmp
EXPOSE 8090
ADD ./target/ms-zuul-server-0.0.1-SNAPSHOT.jar ms-zuul-server.jar
ENTRYPOINT ["java","-jar","/ms-zuul-server.jar"]