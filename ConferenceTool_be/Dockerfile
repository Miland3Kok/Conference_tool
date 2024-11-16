# Builder
FROM openjdk:17-jdk-slim AS build
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew build --stacktrace -x test

# Server
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/conferenceTool_backend-0.0.1-SNAPSHOT.jar conference.jar

# necessary for now
COPY /src/main/resources src/main/resources
CMD ["sh", "-c", "java $JAVA_OPTS -jar conference.jar"]
