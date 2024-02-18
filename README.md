# GitHub Repo Fetch Service

This application retrieves all repositories for a given user that are not forks. 
Additionally, it fetches all branches for each repository along with their last commit SHA.

## How to run

### Using Docker

If Docker is installed and running, and you have Internet access, you can simply run the entire application by executing:

```
docker run -d -p 8080:8080 viepovsky/github-repo-fetch:latest
```

Alternatively, if you have downloaded the docker-compose.yml, you can run:

```
docker compose up -d
```

### Without Docker

If Docker is not installed, follow the instructions below:

- Download the repository
- Run the `FetchApplication` class or simply type `./mvnw spring-boot:run` in terminal

## How to use

To use the application you must make a request to `http://localhost:8080/api/v1/repositories` with the `username` parameter.

For example, to check my repositories, use the following link `http://localhost:8080/api/v1/repositories?username=viepovsky`

## Response schema

- Valid request:

```json
{
    "repositoryName": "Car-Service-Garage-Frontend",
    "ownerLogin": "viepovsky",
    "branches": [
        {
            "branchName": "master",
            "lastCommitSha": "8b04ddb794776628b30b84aeab31977c7ab620e8"
        },
        {
            "branchName": "release-1.0",
            "lastCommitSha": "c4e677f63855db39dc9d53617c50e59d6f6d4932"
        },
        {
            "branchName": "release-1.1",
            "lastCommitSha": "cd0df85ff90ed957de1675c06555529eaae963ed"
        }
    ]
}
```

- Not valid request (user not existing):

```json
{
    "status": 404,
    "message": "404 NOT_FOUND"
}
```

## Note on Request Limits
Please note that this application does not use authentication, and the GitHub API has request limits of 60 requests per hour for a given IP address. 
If you encounter issues due to exceeding this limit, you may need to wait for the limit to reset or consider using authentication with a personal access token for higher request limits.

## Example

### Response for not existing user:
![not_found_response.JPG](src%2Fmain%2Fresources%2Fnot_found_response.JPG)

### Response for existing user:
![response.JPG](src%2Fmain%2Fresources%2Fresponse.JPG)
