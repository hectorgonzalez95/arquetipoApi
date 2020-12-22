FROM frolvlad/alpine-java:jdk8-slim
RUN apk add -U tzdata
RUN cp /usr/share/zoneinfo/America/Santiago /etc/localtime
RUN date
RUN mkdir -p /api-spring-boot/lib/
RUN mkdir -p /api-spring-boot/config/
RUN mkdir -p /api-spring-boot/logs/
ADD bootstrap.yml /api-spring-boot/config
ADD logback.xml /api-spring-boot/config
ADD application-arquetipo-api-messages.properties /api-spring-boot/config
WORKDIR /api-spring-boot
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","lib/app.jar"]
EXPOSE 9000
HEALTHCHECK --interval=2s --retries=15 --timeout=2s --start-period=8s CMD curl --connect-timeout 1 -f http://127.0.0.1:9000/api-spring-boot
ADD arquetipo-api.jar /api-spring-boot/lib/app.jar