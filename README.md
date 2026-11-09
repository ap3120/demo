# Getting Started

This is a simple Spring Boot application with Postgres.

### Environment variables
Create a `.env` file with your secrets following the `.env-sample` and complete the `docker-compose.yml`

### Building the app
`mvn clean install`

### Running the app
1. With Postgres running locally:
    * Create a Postgres database with tables following `init.sql`
    * `mvn spring-boot:run`
2. Using docker compose:
    * Build your image: `docker build -t my_image_name .`
    * `docker compose up -d`

### Deploying with Kubernetes




