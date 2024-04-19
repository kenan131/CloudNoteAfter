FROM openjdk:8-jre-slim

MAINTAINER bin

WORKDIR /app

COPY cloudnote.jar /app

CMD ["java", "-jar", "cloudnote.jar"]