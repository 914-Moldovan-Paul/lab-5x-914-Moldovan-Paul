FROM openjdk:19-jdk-slim
ARG JAR_FILE=target/*.war
COPY ./target/spring-boot-aws.war app.war
ENTRYPOINT ["java", "-jar", "/app.war"]