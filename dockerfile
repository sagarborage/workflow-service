# Stage 1: Build
FROM maven:3.6.3-openjdk-11 as build
WORKDIR /workflow-app
COPY . /workflow-app
RUN mvn -f /workflow-app/pom.xml clean package

# Stage 2: Run
FROM openjdk:11-jre-slim
COPY --from=build /workflow-app/target/workflow-app.jar /usr/local/lib/workflow-app.jar
EXPOSE 8091
ENTRYPOINT ["java","-jar","/usr/local/lib/workflow-app.jar"]