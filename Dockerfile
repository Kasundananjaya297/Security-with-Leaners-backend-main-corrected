FROM openjdk:8
EXPOSE 8080
ADD target/spring-docker.war spring-docker.war
ENTRYPOINT ["java", "-jar", "/spring-docker.war"]