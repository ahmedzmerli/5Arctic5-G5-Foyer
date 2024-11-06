FROM maven:3.9-amazoncorretto-21-alpine
ARG PORT
ARG PROJECT_VERSION=1.0.0
WORKDIR /app
EXPOSE ${PORT}
COPY target/tp-foyer-${PROJECT_VERSION}.jar ./
RUN echo $PORT
ENV PROJECT_VERSION_ENV ${PROJECT_VERSION}
CMD java -jar -Dspring.profiles.active=prod tp-foyer-${PROJECT_VERSION_ENV}.jar
