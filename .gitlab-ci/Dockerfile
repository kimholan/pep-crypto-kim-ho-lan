ARG PCBA_DOCKER_REGISTRY

FROM ${PCBA_DOCKER_REGISTRY}pep-crypto-build-environment/openjdk11-hotspot:latest as runtime-image-java
FROM debian:buster-slim

COPY --from=runtime-image-java /opt/java/openjdk/ /usr/java/default
COPY ./pep-crypto-thorntail-1.0-quarkus-thorntail.jar /
COPY --chown=65534:65534 ./files /
COPY ./jacocoagent.jar /

RUN chmod -v ugo+rx /pep-crypto-entrypoint.sh

ENTRYPOINT ["/pep-crypto-entrypoint.sh"]
