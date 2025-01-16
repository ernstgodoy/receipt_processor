FROM openjdk:23-slim AS build
RUN apt-get update && apt-get install -y maven
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install

FROM eclipse-temurin:23-jdk
WORKDIR /app

COPY --from=build /app/target/receipt-processor-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
