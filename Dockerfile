FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/movie-service-0.0.1-SNAPSHOT.jar movie-service.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "movie-service.jar"]