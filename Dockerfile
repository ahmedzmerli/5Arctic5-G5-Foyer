FROM openjdk:17
EXPOSE 8087
ADD target/tpfoyer-0.0.1.jar tpfoyer-0.0.1.jar
ENTRYPOINT ["java","-jar","/tpfoyer-0.0.1.jar"]