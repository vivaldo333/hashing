FROM openjdk:8-jdk-alpine
ARG JAR_FILE=hashing.jar
COPY ${JAR_FILE} hashing.jar
ENTRYPOINT ["java","-jar","hashing.jar"]
EXPOSE 8080