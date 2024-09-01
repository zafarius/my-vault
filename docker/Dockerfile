FROM alpine:latest as builder

# Install JRE, Update OS and add non-provileged user
RUN export HTTP_PROXY=${PROXY_URL} \
  && export HTTPS_PROXY=${PROXY_URL} \
  && apk add openjdk17-jre-headless --no-cache \
  && apk -U upgrade --no-cache \
  && unset HTTP_PROXY \
  && unset HTTPS_PROXY \
  && addgroup -g 1000 -S appusers \
  && adduser -u 1000 -S appuser -G appusers

WORKDIR application
ARG BUILD_VERSION

COPY vault-${BUILD_VERSION}.jar vault.jar

USER appuser

VOLUME /application/data

ENTRYPOINT ["java", "-jar", "vault.jar"]
