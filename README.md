# YouToddler
[![build-all-and-validate-nightly](https://github.com/cant0r/YouToddler/actions/workflows/build-all-and-validate-nightly.yml/badge.svg)](https://github.com/cant0r/YouToddler/actions/workflows/build-all-and-validate-nightly.yml)  
Group project of the people of the Manhattan Project.

## Description
An on-premise download manager for youtube-dlp.

## Installation Guide
You can either build the project yourself or use the prebuilt Docker images.

### Easy steps
1. Make sure you have [Docker](https://www.docker.com/) installed on your system.   
2. Change directory to the project's root direectory
3. Execute `docker compose up`
4. When you're inside the shell execute `java -jar youtoddler-spring-0.7.2.jar`

You can access the Frontend at <http://localhost:8080>.   
You can access the Swagger Documentation at <http://localhost:9090>.   

### Manual steps
**To build the project yourself make sure you have the following installed on your system:**
* .NET 7 -- Used by NUKE
* Java 1.8 -- Used to execute the built _fat_ jar of the WebAPI component
  * > **NOTE**: We only support 1.8! No other Java release will work!
* [Docker](https://www.docker.com/)

1. Change directory to the project's root direectory
2. Login to your Docker Hub account: `docker login`
3. Compile the project with `build.cmd PublishAll --configuration Release --docker_username <user> --docker_password <password>`
4. Execute `docker compose up`

You can access the Frontend at <http://localhost:8080>.   
You can access the Swagger Documentation at <http://localhost:9090>.   

### YouToddler CLI examples
Downloading metadata   
`.\YouToddlerCLI describe -t https://www.youtube.com/watch?v=ycHVUvvOwzY`  
Downloading video content   
`.\YouToddlerCLI download -v 136 -a 140 -t https://www.youtube.com/watch?v=ycHVUvvOwzY`  
Downloading user history   
`.\YouToddlerCLI history`  
Cleaning up working directories   
`.\YouToddlerCLI clean`    

## Documentation
[Here](https://github.com/cant0r/YouToddler/tree/master/docs)

## Contibuting
Please visit the [CONTRIBUTING.MD](https://github.com/cant0r/YouToddler/blob/master/CONTRIBUTING.md) document on how to contribute to the project.

## Team
|GitHub Handler| Work|
|:------------:|:----|
|[@cant0r](https://github.com/cant0r/)|YouToddler Class Library and CLI|
|[@TheUsernameIsNotTaken](https://github.com/TheUsernameIsNotTaken)|YouToddler WebAPI|
|[@szemand](https://github.com/szemand)|YouToddler Frontend|
|[@Pajaik](https://github.com/Pajaik)|YouToddler Documentation, Demos, Sales|
