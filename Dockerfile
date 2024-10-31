# Use the official OpenJDK image from the Docker Hub
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the jar file into the container
COPY target/tp-foyer-1.0.0-SNAPSHOT.jar app.jar

# Expose the port your app runs on
EXPOSE 8089

# Command to run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
