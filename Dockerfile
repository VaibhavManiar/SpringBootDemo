FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
ENV NLS_LANG="AMERICAN_AMERICA.UTF8"
ENV LC_ALL="en_US.UTF-8"
COPY ${JAR_FILE} app.jar
EXPOSE 5080
ENTRYPOINT ["java","-jar","/app.jar"]

# docker run --rm -itd --network <host> --name <CONTAINER_NAME> <IMAGE_ID>