FROM openjdk:11
COPY ./target/MyTaskGuru-0.0.1-SNAPSHOT.jar ./
WORKDIR ./
CMD ["java", "-jar", "MyTaskGuru-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]
