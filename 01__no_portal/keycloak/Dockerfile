FROM registry.access.redhat.com/ubi9 AS ubi-micro-build
RUN mkdir -p /mnt/rootfs
RUN dnf install --installroot /mnt/rootfs curl --releasever 9 --setopt install_weak_deps=false --nodocs -y; \
    dnf --installroot /mnt/rootfs clean all

FROM quay.io/keycloak/keycloak:latest
COPY --from=ubi-micro-build /mnt/rootfs /
ENV KEYCLOAK_ADMIN=admin
ENV KEYCLOAK_ADMIN_PASSWORD=admin
ENV KC_HOSTNAME=localhost
ADD realm/hillamicro.json .
RUN ["/opt/keycloak/bin/kc.sh", "import", "--file", "hillamicro.json"]
ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start-dev", "--hostname=host.docker.internal"]
EXPOSE 8080
HEALTHCHECK --start-period=15s CMD [ "curl", "-f", "http://localhost:8080/realms/hillamicro" ]
