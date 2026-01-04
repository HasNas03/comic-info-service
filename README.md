**Comic Catalog Project**

- The Comic Catalog is a personal-use project aiming to develop a custom application for storing comic book reviews and reading progress


- This is a list of repositories for all microservices/servers used in this project
    - [Comic Catalog Microservice (primary client-facing API)](https://github.com/HasNas03/comic-catalog-service)
    - [Comic Info Service Microservice](https://github.com/HasNas03/comic-info-service)
    - [Comic Rating Microservice](https://github.com/HasNas03/comic-rating-service)
    - [Comic Catalog Discovery Server](https://github.com/HasNas03/discovery-server)


- The project is also a gateway for me to learn and practice:
    - backend development (Java & Spring) best practices
    - developing REST APIs
    - connecting to external APIs
    - cross-microservice integration/authentication
    - testing and security
    - external database integration
    - Cloud integration/hosting

- Technologies
    - Current technologies: Java, Spring (Boot, Web), Netflix Eureka, Maven, Git
    - Future technologies: (SQL/MongoDB), Docker, AWS, Spring Security
---

---
**Comic Info Microservice**

- This repository stores metadata for major comic runs/events
- The Comic Info API provides REST endpoints for the Comic Catalog Microservice to pull from a list of popular comics


- Architecture (suggestions/improvements are welcome/encouraged!):
    - **Comic (Entity)**
        - Comic(String comicId, String comicName, String comicDesc)
        - JPA entity representing a stored comic record, including the comic Id (userId), the comic name (comicName), and the comic description (comicDesc)

    - **ComicController**
        - REST controller for the Info microservice (/comics)

    - **ComicService**
        - Service layer containing the business logic

    - **ComicRepository**
        - TBD
