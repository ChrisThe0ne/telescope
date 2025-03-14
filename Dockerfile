# Use an official OpenJDK runtime as a base image
FROM eclipse-temurin:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the host machine
COPY target/telescope-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port (matches application.yml)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]