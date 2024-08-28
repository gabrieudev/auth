FROM ubuntu:24.04 AS build

RUN apt-get update \
    && apt-get install openjdk-17-jdk -y \
    && apt-get clean
COPY . .

RUN apt-get install maven -y \
    && mvn clean install -DskipTests \
    && apt-get clean

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /target/auth-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]