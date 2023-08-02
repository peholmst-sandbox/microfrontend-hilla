FROM maven:3-eclipse-temurin-17-alpine AS build
ADD . $HOME
RUN --mount=type=cache,target=/root/.m2 mvn package

FROM eclipse-temurin:17-jre-alpine
COPY --from=build /target/*.jar eureka-app.jar
ENTRYPOINT ["java", "-jar", "/eureka-app.jar"]
EXPOSE 8761
