FROM openjdk:17
ADD  target/vote-service-1.0.0.jar service.jar
EXPOSE 9070
ENTRYPOINT ["java", "-jar","service.jar"]
