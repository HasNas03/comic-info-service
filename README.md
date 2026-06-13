**Comic Catalog Project**

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
**Comic Info Microservice**

- This repository stores metadata for major comic runs/events
- The Comic Info API provides REST endpoints for the Comic Catalog Microservice to pull comic data from


- Architecture (suggestions/improvements are welcome/encouraged!):
    - **Comic (Model)**
        - Comic(UUID comicId, String comicTitle, String comicIssue, String comicStartYear, String comicDesc)

    - **ComicController**
        - REST controller for the Comic Info Microservice (/comics)
        - handles basic HTTP GET/POST/PUT/DELETE methods

    - **ComicService**
        - Service layer containing the business logic functions to process HTTP methods

    - **ComicRepository**
        - TBD (an in-memory data store is currently used while JPA is not implemented)
