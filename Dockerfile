FROM openjdk:8u171-jdk-alpine3.8
COPY core-1.0.jar /app.jar
ADD config /config
ADD libs /libs
CMD ["java", "-jar", "-Dspring.profiles.active=dev", "app.jar"]