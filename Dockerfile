FROM gradle:8.1-jdk11 AS build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

FROM amazoncorreto:11
ENV PORT 8080
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/ktor-server.jar

ENTRYPOINT ["java","-jar","/app/ktor-server.jar"]
