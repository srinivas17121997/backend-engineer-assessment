# Use the official OpenJDK image as a base image
FROM openjdk:23-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY build/libs/app-0.0.1-SNAPSHOT.jar /app/app.jar

# Specify the command to run your application
CMD ["java", "-jar", "app.jar"]
