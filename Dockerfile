FROM ubuntu:latest AS BUILD

RUN apt update
RUN apt install openjdk-21-jdk -y
COPY . .

RUN apt install maven -y
RUN mvn clean install

FROM openjdk:21-jdk-slim

EXPOSE 8080

COPY --from=BUILD /target/api-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]