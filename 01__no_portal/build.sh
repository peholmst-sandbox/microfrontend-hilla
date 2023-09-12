#!/bin/sh
docker build -t hillamicro/noportal/portal-shell portal-shell/
docker compose build
../mvnw -f portal-shell/pom.xml clean install
../mvnw -f portal-gateway/pom.xml clean package
../mvnw -f microfrontend-a/pom.xml clean package
../mvnw -f microfrontend-b/pom.xml clean package
