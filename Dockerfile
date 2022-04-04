#Execute o build do Jar : mvn clean package 
FROM openjdk:8-jdk-slim
ARG JAR_FILE=portal_spring_thymeleaf/target/*.jar
COPY ${JAR_FILE} app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom ","-jar","/app.jar"]