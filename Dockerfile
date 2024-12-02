FROM gradle:8.11.1-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN ./gradlew build -x test

FROM amazoncorretto:21
USER root
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/library-api.jar
ENTRYPOINT ["java","-jar","/app/library-api.jar", "--spring.profiles.active=prod"]