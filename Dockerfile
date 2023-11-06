# Builder Stage
FROM --platform=$BUILDPLATFORM maven:3.8.5-eclipse-temurin-17 AS builder

WORKDIR /workdir/server

# Gradle files
COPY build.gradle /workdir/server/build.gradle
COPY gradle ./gradle
COPY gradlew .

# Gradle build
RUN ./gradlew clean build

# Copy the rest and do a full build
COPY src /workdir/server/src
RUN ./gradlew build && ls /workdir/server/build/libs/

# Dev Environment Stage
FROM builder AS dev-envs

RUN apt-get update && \
    apt-get install -y --no-install-recommends git && \
    useradd -s /bin/bash -m vscode && \
    groupadd docker && \
    usermod -aG docker vscode

COPY --from=gloursdocker/docker / /
CMD ["gradlew", "bootRun"]

# Prepare for Production
FROM builder as prepare-production

RUN mkdir -p target/dependency
WORKDIR /workdir/server/target/dependency

COPY --from=builder /workdir/server/build/libs/*.jar .
RUN jar -xvf ./*.jar

# Production Image
FROM eclipse-temurin:17-jre-focal

EXPOSE 10020

ARG DEPENDENCY=/workdir/server/target/dependency
COPY --from=prepare-production ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=prepare-production ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=prepare-production ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.bestrongkids.authorizationserver.AuthorizationserverApplication"]
