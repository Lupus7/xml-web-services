FROM openjdk:8-jdk-alpine
COPY entrypoint.sh /entrypoint.sh
COPY target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway-0.0.1-SNAPSHOT.jar
COPY consul-client.json /consul-config/consul-client.json
RUN apk --no-cache add \
    curl \
    unzip \
    && curl https://releases.hashicorp.com/consul/1.7.2/consul_1.7.2_linux_amd64.zip -o consul.zip \
    && unzip consul.zip \
    && chmod +x consul \
    && rm -f consul.zip \
    && chmod +x /entrypoint.sh \
    && apk --no-cache del \
    && mkdir consul-data \
    curl \
    unzip
CMD ["sh","/entrypoint.sh"]
