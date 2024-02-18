# GitHub Repo Fetch Service

This application retrieves all repositories for a given user that are not forks. 
Additionally, it fetches all branches for each repository along with their last commit SHA.

## How to run

### Using Docker

If Docker is installed and running you can simply run the entire application by executing 
```
docker run -d -p 8080:8080 viepovsky/github-repo-fetch:latest
```

or if you download docker-compose.yml then you can run
```
docker compose up -d
```

### Without Docker

If Docker is not installed, follow the instructions below:

- Run `FetchApplication` class or simply type `./mvnw spring-boot:run` in terminal

## How to use

To use the application you must make request to `http://localhost:8080/api/v1/repositories` with parameter `username`

You can check my repositories under that link: `http://localhost:8080/api/v1/repositories?username=viepovsky`

## Example
