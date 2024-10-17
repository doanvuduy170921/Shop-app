FROM openjdk:17

WORKDIR /app


COPY target/shopapp-0.0.1-SNAPSHOT.jar shopapp-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","./shopapp-0.0.1-SNAPSHOT.jar"]

