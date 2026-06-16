## Comic Catalog Project

- The Comic Catalog is a personal-use project aiming to develop a custom application for storing comic book reviews and reading progress


- This is a list of repositories for all microservices/servers used in this project
    - [Comic Catalog Microservice (primary client-facing API)](https://github.com/HasNas03/comic-catalog-service)
    - [Comic Info Service Microservice](https://github.com/HasNas03/comic-info-service)
    - [Comic Rating Microservice](https://github.com/HasNas03/comic-rating-service)
    - [Comic Catalog Discovery Server](https://github.com/HasNas03/discovery-server)


- The project is also a gateway for me to learn and practice:
    - backend development (Java & Spring) best practices
    - internal/external REST APIs
    - cross-microservice integration/authentication
    - external database and cloud integration

- Technologies
    - Current: Java, Spring (Boot, Web), Netflix Eureka, Maven, Git
    - Future : (SQL/MongoDB), Docker, AWS, Spring Security, external APIs
---

---
## Comic Info Microservice

- This repository stores metadata for major comic runs/events
- The Comic Info API provides REST endpoints for the Comic Catalog Microservice to pull comic data from
- It temporarily uses an in-memory Map<UUID, Comic> that is recreated every time the app starts


### Model

`Comic`
- `UUID id`
- `String title`
- `String publisher`
- `Integer startYear`
- `String description`

## Endpoints
```text
GET    /comics
GET    /comics/{comicId}
POST   /comics
PUT    /comics/{comicId}
DELETE /comics/{comicId}
```
