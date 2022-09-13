FROM amazoncorretto:11.0.15
VOLUME /tmp
COPY build/libs/product-service-1.0.jar ProductService.jar
ENTRYPOINT ["java", "-jar", "ProductService.jar"]