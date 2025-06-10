FROM gradle:8.4-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

FROM ubuntu/jre:11-24.04_edge
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8071
ENTRYPOINT ["java", "-jar", "app.jar"]
