# Étape de build
FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app

# Copier le fichier pom.xml et télécharger les dépendances Maven
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copier le code source de l'application
COPY src ./src

# Compiler le projet et générer le fichier JAR
RUN mvn clean package -DskipTests

# Étape d'exécution
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copier le fichier JAR généré depuis l'étape de build
COPY --from=build /app/target/tp-foyer-5.1.0.jar app.jar

EXPOSE 8089

# Démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
