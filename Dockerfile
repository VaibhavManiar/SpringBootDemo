FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 5080
ENTRYPOINT ["java","-jar","/app.jar"]

# docker run --rm -itd --network <host> --name <CONTAINER_NAME IMAGE_ID>