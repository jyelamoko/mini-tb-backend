# Étape 1 : Build
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Image finale
FROM eclipse-temurin:21-jre
WORKDIR /work/
COPY --from=build /app/target/quarkus-app /work/
EXPOSE 9001
CMD ["java", "-jar", "/work/quarkus-run.jar"]
