FROM alpine:latest

# Link published image to repo
LABEL org.opencontainers.image.source="https://github.com/zafarius/my-vault"

# Install JRE, Update OS and add non-provileged user
RUN export HTTP_PROXY=${PROXY_URL} \
  && export HTTPS_PROXY=${PROXY_URL} \
  && apk add openjdk17-jre-headless --no-cache \
  && apk -U upgrade --no-cache \
  && unset HTTP_PROXY \
  && unset HTTPS_PROXY \
  && addgroup -g 1000 -S appusers \
  && adduser -u 1000 -S appuser -G appusers

WORKDIR /application
ARG BUILD_VERSION

COPY vault-${BUILD_VERSION}.jar vault.jar

VOLUME /application
RUN chown appuser:appusers -R  /application

USER appuser


ENTRYPOINT ["java", "-jar", "vault.jar"]
