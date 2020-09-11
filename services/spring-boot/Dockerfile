FROM java:8-jdk-alpine

RUN mkdir /app
WORKDIR /app

COPY ./target/eventik-rest-api-0.0.1-SNAPSHOT.jar /app/

RUN sh -c 'touch eventik-rest-api-0.0.1-SNAPSHOT.jar'