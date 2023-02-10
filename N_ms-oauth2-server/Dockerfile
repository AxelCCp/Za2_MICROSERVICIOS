FROM openjdk:12
VOLUME /tmp
EXPOSE 9100
ADD ./target/ms-oauth2-server-0.0.1-SNAPSHOT.jar ms-oauth2-server.jar
ENTRYPOINT ["java","-jar","/ms-oauth2-server.jar"]