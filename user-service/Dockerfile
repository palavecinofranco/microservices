FROM openjdk:17-jdk-alpine as builder

WORKDIR /app/user-msvc

COPY ./pom.xml /app
COPY ./user-service/.mvn ./.mvn
COPY ./user-service/mvnw .
COPY ./user-service/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./user-service/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/user-msvc/target/user-service-0.0.1-SNAPSHOT.jar .

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "user-service-0.0.1-SNAPSHOT.jar"]