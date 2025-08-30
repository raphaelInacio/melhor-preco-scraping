FROM maven:3.8.8-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn clean install -DskipTests

FROM build AS test
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar

CMD ["mvn", "test"]