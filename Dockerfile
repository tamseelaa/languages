# -------- Build stage --------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# -------- Runtime stage --------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Install only required JavaFX dependencies
RUN apt-get update && apt-get install -y \
    wget unzip libgtk-3-0 libgl1 libnss3 libxext6 libxrender1 libxtst6 libxrandr2 \
    && rm -rf /var/lib/apt/lists/*

# Download JavaFX SDK (direct link)
RUN wget https://download2.gluonhq.com/openjfx/20.0.2/openjfx-20.0.2_linux-x64_bin-sdk.zip -O javafx.zip \
    && unzip javafx.zip -d /opt/javafx \
    && rm javafx.zip

# Copy the JAR built in the build stage
COPY --from=build /app/target/*.jar app.jar

# Run JavaFX app
CMD ["java", "--module-path", "/opt/javafx/lib", "--add-modules", "javafx.controls,javafx.fxml", "-jar", "app.jar"]