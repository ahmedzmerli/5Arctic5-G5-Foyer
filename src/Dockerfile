FROM openjdk:17-jdk-alpine

# Expose the port your app is running on
EXPOSE 8089

# Add the packaged JAR file to the container
ADD target/tp-foyer-1.0.0.jar tp-foyer-1.0.0.jar

# Set the entrypoint for your app
ENTRYPOINT ["java", "-jar", "/tp-foyer-1.0.0.jar"]
