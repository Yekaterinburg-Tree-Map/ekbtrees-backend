FROM adoptopenjdk/openjdk11:alpine

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw mvnw
COPY pom.xml pom.xml
COPY src ./src

CMD ["./mvnw", "test"]
