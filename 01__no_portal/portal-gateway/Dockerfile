FROM maven:3-eclipse-temurin-17-alpine AS build
ADD . $HOME
RUN --mount=type=cache,target=/root/.m2 mvn package

FROM eclipse-temurin:17-jre-alpine
COPY --from=build /target/*.jar portal-gateway.jar
RUN apk --no-cache add curl
ENTRYPOINT ["java", "-jar", "/portal-gateway.jar"]
EXPOSE 8080
HEALTHCHECK --start-period=15s CMD [ "curl", "-f", "http://localhost:8080/actuator/health" ]
