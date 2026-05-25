FROM gradle:8.9-jdk17 AS builder

WORKDIR /workspace

# Copy the repo into the image. The root .dockerignore keeps this small enough
# for Render while still giving Gradle the complete project layout.
COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew :visit-notifier:jar --no-daemon

FROM eclipse-temurin:17-jre

WORKDIR /app

ENV PORT=10000
EXPOSE 10000

COPY --from=builder /workspace/visit-notifier/build/libs/visit-notifier-1.0-SNAPSHOT-all.jar /app/visit-notifier.jar

# Render injects PORT at runtime; the app also respects a local .env for dev.
CMD ["java", "-jar", "/app/visit-notifier.jar"]
