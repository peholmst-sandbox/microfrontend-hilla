FROM hillamicro/noportal/portal-shell AS portal-shell

FROM maven:3-eclipse-temurin-17-alpine AS build
RUN apk --no-cache add nodejs npm

# It appears Vite does not like to run builds from /
RUN mkdir -p /build
ENV HOME=/build
WORKDIR $HOME

COPY --from=portal-shell /target/*.jar $HOME/portal-shell.jar
RUN --mount=type=cache,target=/root/.m2 mvn install:install-file -Dfile=$HOME/portal-shell.jar
ADD . $HOME

RUN --mount=type=cache,target=/root/.m2 mvn clean package

FROM eclipse-temurin:17-jre-alpine
COPY --from=build /build/target/*.jar frontend.jar
RUN apk --no-cache add curl
ENV PORT 8080
ENTRYPOINT ["java", "-jar", "/frontend.jar"]
EXPOSE 8080
HEALTHCHECK --start-period=15s CMD [ "curl", "-f", "http://localhost:8080/actuator/health" ]
