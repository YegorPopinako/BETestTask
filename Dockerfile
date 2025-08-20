FROM maven:3.9.9-amazoncorretto-21-alpine AS build
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

FROM openjdk:21
WORKDIR /app
COPY --from=build /app/target/*.jar /app
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar *.jar"]
