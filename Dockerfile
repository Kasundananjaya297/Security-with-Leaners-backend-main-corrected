# Description: Dockerfile for the Java 17 image
#FROM openjdk:17-jdk-slim
FROM ubuntu:20.04

# Set the environment variable to avoid interactive prompts
ENV DEBIAN_FRONTEND=noninteractive
ENV TZ=Etc/UTC

# Install required packages including MySQL and Java
RUN apt-get update && \
    apt-get install -y tzdata && \
    ln -fs /usr/share/zoneinfo/$TZ /etc/localtime && \
    dpkg-reconfigure --frontend noninteractive tzdata && \
    apt-get install -y openjdk-17-jdk mysql-server && \
    rm -rf /var/lib/apt/lists/* \

# Set the working directory
WORKDIR /src


# Expose the application port and MySQL port
EXPOSE 8080
EXPOSE 3306

# Copy the environment file and JAR file into the container
COPY src/main/resources/app.env /src/app.env
COPY target/*.jar /src/app.jar

# Copy initialization SQL script to initialize the database
COPY src/main/resources/init.sql /docker-entrypoint-initdb.d/

# Add a custom entrypoint script
COPY src/main/resources/docker-entrypoint.sh /usr/local/bin/docker-entrypoint.sh
RUN chmod +x /usr/local/bin/docker-entrypoint.sh

# Define the entry point for the container
ENTRYPOINT ["/usr/local/bin/docker-entrypoint.sh"]
