FROM eclipse-temurin:21-jdk-jammy AS builder

WORKDIR /workspace

COPY .mvn .mvn
COPY mvnw pom.xml ./

RUN chmod +x mvnw && ./mvnw -B -DskipTests dependency:go-offline

COPY src src

RUN ./mvnw -B -DskipTests package

FROM eclipse-temurin:21-jre-jammy AS runtime

RUN groupadd --system spring && useradd --system --gid spring spring

WORKDIR /app

COPY --from=builder /workspace/target/*.war app.war

USER spring:spring

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "app.war" ]