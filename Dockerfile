FROM bellsoft/liberica-openjdk-alpine-musl
COPY ./build/libs/metrics-1.0.1.1.jar .
CMD ["java","-jar","metrics-1.0.1.1.jar"]