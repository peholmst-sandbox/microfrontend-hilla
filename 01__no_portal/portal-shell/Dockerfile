FROM maven:3-eclipse-temurin-17-alpine AS build
ADD . $HOME
RUN --mount=type=cache,target=/root/.m2 mvn package
