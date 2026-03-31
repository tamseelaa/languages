# -------- Build stage --------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# -------- Runtime stage --------
FROM openjdk:17-slim
WORKDIR /app

# Download JavaFX SDK
RUN apt-get update && apt-get install -y wget unzip libgtk-3-0 libgl1-mesa-glx \
    && wget https://gluonhq.com/download/javafx-20/ -O javafx.zip \
    && unzip javafx.zip -d /opt/javafx \
    && rm javafx.zip

COPY --from=build /app/target/*.jar app.jar

CMD ["java",
     "--module-path", "/opt/javafx/lib",
     "--add-modules", "javafx.controls,javafx.fxml",
     "-jar", "app.jar"]