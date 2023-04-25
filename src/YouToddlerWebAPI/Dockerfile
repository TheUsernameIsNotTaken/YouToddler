FROM eclipse-temurin:8-focal

# We assume the project is already compiled
COPY YouToddlerWebAPI/target/*.jar /webapi/
COPY YouToddlerCLI/publish/* /webapi/

WORKDIR /webapi/
EXPOSE 9090