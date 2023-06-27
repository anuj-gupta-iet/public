# For Java 8, try this
FROM openjdk:8-jdk-alpine

# Refer to Maven build -> finalName
ARG JAR_FILE=target/spring-1-0.0.1-SNAPSHOT.jar

# cd /opt/app
WORKDIR /opt/app

# cp target/spring-boot-web.jar /opt/app/spring-boot.jar
COPY ${JAR_FILE} spring-boot.jar

# java -jar /opt/app/spring-boot.jar
ENTRYPOINT ["java","-jar","spring-boot.jar"]