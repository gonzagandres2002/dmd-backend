#Build stage
FROM maven:3-eclipse-temurin-21-alpine AS build

#Set the working directory
WORKDIR /app

#Copy the pom.xml and install the dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

#Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

#Run stage
From eclipse-temurin:21

#Set the working directory
WORKDIR /app

#Copy the built JAR file from the build stage
COPY --from=build /app/target/client-management-0.0.1-SNAPSHOT.jar .

#Expose the port
EXPOSE 8080

#Specify the command to run the application
ENTRYPOINT ["java", "-jar", "/app/client-management-0.0.1-SNAPSHOT.jar"]