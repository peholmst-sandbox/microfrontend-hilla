FROM maven:3-eclipse-temurin-17-alpine AS build
ADD . $HOME
RUN --mount=type=cache,target=/root/.m2 mvn package

FROM eclipse-temurin:17-jre-alpine
COPY --from=build /target/*.jar eureka-app.jar
RUN apk --no-cache add curl
ENTRYPOINT ["java", "-jar", "/eureka-app.jar"]
EXPOSE 8761
HEALTHCHECK --start-period=15s CMD [ "curl", "-f", "http://localhost:8761/actuator/health" ]
