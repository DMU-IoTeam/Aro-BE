FROM openjdk:17-jdk-slim AS builder

WORKDIR /workspace/app

RUN apt-get update && apt-get install -y dos2unix \
    && rm -rf /var/lib/apt/lists/*

COPY gradlew .
RUN dos2unix gradlew
RUN chmod +x gradlew
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN ./gradlew build -x test

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /workspace/app/build/libs/*.jar app.jar
COPY --from=builder /workspace/app/src/main/resources/firebase-service-key.json .

ENTRYPOINT ["java","-jar","/app/app.jar"]