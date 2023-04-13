# YouToddler
[![build-all-and-validate-nightly](https://github.com/cant0r/YouToddler/actions/workflows/build-all-and-validate-nightly.yml/badge.svg)](https://github.com/cant0r/YouToddler/actions/workflows/build-all-and-validate-nightly.yml)  
Group project of the people of the Manhattan Project.

## Description
An on-premise download manager for youtube-dlp.

## Installation Guide
Download the latest release artifact.
* For the Web Interface run the appropriate `start` script for you environment.
* For the CLI just invoke the tool with the desired arguments.

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

## How to compile the project locally?
1. Clone down the repository
2. Change directory into the cloned down respository 
3. Open a shell
4. Execute `nuke`
  * If you don't have NUKE installed then [visit the project's website](https://nuke.build/docs/introduction/) for instructions.

## Team
|GitHub Handler| Work|
|:------------:|:----|
|[@cant0r](https://github.com/cant0r/)|YouToddler Class Library and CLI|
|[@TheUsernameIsNotTaken](https://github.com/TheUsernameIsNotTaken)|YouToddler WebAPI|
|[@szemand](https://github.com/szemand)|YouToddler Frontend|
|[@Pajaik](https://github.com/Pajaik)|YouToddler Documentation, Demos, Sales|
