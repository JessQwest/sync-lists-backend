# Use an official lightweight OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file into the container
COPY sync-lists-backend*.jar sync-lists-backend.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "sync-lists-backend.jar"]