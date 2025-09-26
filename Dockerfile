# -------------------- STAGE 1: BUILD --------------------
# Uses a base image with Maven and JDK 17 to compile your code
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
# Builds the executable JAR, skipping tests
RUN mvn clean package -DskipTests

# -------------------- STAGE 2: RUNTIME --------------------
# Uses a minimal JRE (Java Runtime Environment) for the final app
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copies the built JAR file from the first stage into the final, small image
COPY --from=build /app/target/*.jar app.jar

# Spring Boot default port is 8080. Render uses $PORT (usually 10000).
EXPOSE 10000 

# The command to start the application, configured to use Render's port
ENTRYPOINT ["java", "-Dserver.port=$PORT", "-jar", "app.jar"]