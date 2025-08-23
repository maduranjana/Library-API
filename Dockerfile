
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/library-0.0.1-SNAPSHOT.jar library-app.jar

EXPOSE 8080

ENV DB_URL=jdbc:mysql://localhost:3306/lib_system
ENV DB_USERNAME=root
ENV DB_PASSWORD=1234
ENV SERVER_PORT=8080
ENV SHOW_SQL=true
ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "library-app.jar"]
