FROM openjdk:8-jdk-alpine

WORKDIR /app
COPY ./*.jar /app/app.jar

EXPOSE 9100
EXPOSE 9200

CMD ["java", "-jar", "-Dspring.profiles.active=docker", "app.jar"]