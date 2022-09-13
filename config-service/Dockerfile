FROM amazoncorretto:11.0.15
VOLUME /tmp
COPY build/libs/config-service-1.0.jar ConfigService.jar
ENTRYPOINT ["java", "-jar", "ConfigService.jar"]