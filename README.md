
# API LOTTERY DOMAIN GAME
[![Build](https://github.com/Malagutte/lottery-domain-game/actions/workflows/build.yml/badge.svg)](https://github.com/Malagutte/lottery-domain-game/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Malagutte_lottery-domain-game&metric=alert_status)](https://sonarcloud.io/dashboard?id=Malagutte_lottery-domain-game)
# Description
This application consists of taking the lottery data and saving it in a database to simplify the information.

[**swagger**  documentation.](https://lottery-domain-game.herokuapp.com/swagger-ui/)

## Prerequisites

For the execution of this project, the following are necessary:

- Git
- Java 15+
- Maven 3.6.1+
- Docker Compose 1.24.x

## Project Execution
In order to run this project, it is necessary to install all of its dependencies and configure the environment, which contains the connections


### Connections Configuration
This project depends on connections with `MongoDB` to run, so before running it, it is necessary that these services are available first. For the development, the `docker-compose` is used to orchestrate a set of containers with the necessary services.

To run the services with `docker-compose` it is first necessary to navigate to the folder where the` docker-compose.yml` file is located (usually at the root of the project) and execute the command below.
```bash
docker-compose up -d
```

After running the command above, it is possible to check the status of the containers using the following command

- all services must have the status 'UP'.

```bash
docker-compose ps
```


### Execution 
In your IDE look for the main class `DomainGameApplication` and in the jvm variables you need to add the following value `-Dspring.profiles.active = local`, just click on run and access  access [swagger.](http://localhost:8080/swagger-ui/)




