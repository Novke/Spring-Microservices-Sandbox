FROM openjdk:17-jdk-alpine

VOLUME /tmp

COPY imeFajla.jks imeFajla.jks

COPY target/ConfigServer-0.0.1-SNAPSHOT.jar ConfigServer.jar

ENTRYPOINT ["java", "-jar", "ConfigServer.jar"]