# quizzical-user-service
## About
This repository contains operations related to Quizzical's users
these users include Players and Moderators for now.
### Calling the API
Use the api key by adding x-api-key header to your http requests.

### Running the service
Add `-Dspring.profiles.active=dev` to VM arguments while creating dev run config (same with prod).
Add the following environment variables to your run config
```
API_KEY: 9cecc4a4-a9f0-4430-9c0d-3c8cb45ad7f4
```

### Dependencies
- Spring boot
- Dev tools
- Spring Data Jpa
- PostgreSQL starter
- Lombok
- JUnit5

```
docker run -d -p 5432:5432 --name quizzical-user-dev -e POSTGRES_PASSWORD=admin postgres
```
