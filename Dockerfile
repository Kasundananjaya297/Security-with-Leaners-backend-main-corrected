# Use the official OpenJDK image as a base image
FROM openjdk:17-jdk-alpine
MAINTAINER baeldung.com

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY /target/charika-leaners.jar /app/app.jar

# Expose the port that your application runs on (if needed)
EXPOSE 8080

# Run the JAR file when the container starts
CMD ["java", "-jar", "app.jar"]

