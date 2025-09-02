# Stage 1: Build the JAR file
FROM maven:3.8.6-openjdk-17-slim AS build

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the app
COPY src /app/src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/library-0.0.1-SNAPSHOT.jar library-app.jar

# Create a non-root user to run the application for security
RUN useradd -m springuser
USER springuser

EXPOSE 8080

# Environment variables for the application
ENV DB_URL=jdbc:mysql://localhost:3306/lib_system
ENV DB_USERNAME=root
ENV DB_PASSWORD=1234
ENV SERVER_PORT=8080
ENV SHOW_SQL=true
ENV SPRING_PROFILES_ACTIVE=prod

# run the Spring Boot application
ENTRYPOINT ["java", "-jar", "library-app.jar"]
