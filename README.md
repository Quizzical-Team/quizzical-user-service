# quizzical-user-service
## About
This repository contains operations related to Quizzical's users
these users include Players and Moderators for now.
This repository accesses a PostgreSQL database and adopts the MVC pattern.
Also operations related to authentication and authorization are handled here.
### API KEY: 9cecc4a4-a9f0-4430-9c0d-3c8cb45ad7f4
Use the api key by adding x-api-key header to your http requests.

### Dependencies
- Spring boot
- Dev tools
- Spring Data Jpa
- PostgreSQL starter
- Lombok
- AssertJ
- JUnit5

docker run -d -p 5432:5432 --name quizzical-user-dev -e POSTGRES_PASSWORD=admin postgres
