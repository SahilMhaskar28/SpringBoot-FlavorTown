# ---------- Build stage ----------
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /build

# Copy Maven wrapper and pom.xml first (for caching)
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw mvnw
RUN chmod +x mvnw

# Copy source code
COPY src ./src

# Package the application (skip tests for faster build)
RUN ./mvnw -B -DskipTests clean package

# ---------- Run stage ----------
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the built jar from build stage
COPY --from=build /build/target/ThymeLeaf_project-0.0.1-SNAPSHOT.jar app.jar

# Expose the app port
EXPOSE 8080

# JVM options can be overridden at runtime
ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
