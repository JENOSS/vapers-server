FROM amazoncorretto:11.0.15
VOLUME /tmp
COPY build/libs/gateway-service-1.0.jar GatewayService.jar
ENTRYPOINT ["java", "-jar", "GatewayService.jar"]