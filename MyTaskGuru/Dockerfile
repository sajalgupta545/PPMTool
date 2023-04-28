FROM openjdk:8-jdk-alpine
COPY ./target/MyTaskGuru-0.0.1-SNAPSHOT.jar ./
WORKDIR ./
EXPOSE 3002
CMD ["java", "-jar", "MyTaskGuru-0.0.1-SNAPSHOT.jar"]