FROM gradle:8.9-jdk17 AS builder

WORKDIR /workspace

COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew :visit-notifier:jar --no-daemon

FROM eclipse-temurin:17-jre

WORKDIR /app

ENV PORT=10000
EXPOSE 10000

COPY --from=builder /workspace/visit-notifier/build/libs/visit-notifier-1.0-SNAPSHOT-all.jar /app/visit-notifier.jar

ENTRYPOINT ["java", "-jar", "/app/visit-notifier.jar"]
