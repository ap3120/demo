# Getting Started

This is a simple Spring Boot application with Postgres.

### Environment variables
Create a `.env` file with your secrets following the `.env-sample`.
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
1. Create a `postgres-secrets.yaml` following the `postgres-secrets-sample.yaml`
2. Start minikube: `minikube start`
3. Login to docker hub: `docker login`
4. Push image to docker hub: `docker push my_image_name`
5. Create Postgres config and credentials:
    * `kubectl apply -f kubernetes/postgres-configmap.yaml`
    * `kubectl apply -f kubernetes/postgres-secret.yaml`
6. Create Postgres services:
    * `kubectl apply -f kubernetes/postgres-headless-service.yaml`
    * `kubectl apply -f kubernetes/postgres-service.yaml`
7. Deploy Postgres: `kubectl apply -f kubernetes/postgres-statefulset.yaml`
8. Deploy Spring boot application:
    * `kubectl apply -f kubernetes/demo-deployment.yaml`
    * `kubectl apply -f kubernetes/demo-service.yaml`

### Verifying the deployment
Check the pods: `kubectl get po` </br>
Check the logs: `kubectl logs pod_name` </br>

### Using the application
Get the port from `kubectl get svc`</br>
Get the url from `kubectl get nodes -o wide`</br>
Or simply the url and port from `minikube service service_name --url`</br>
Send requests:
   * `curl -XGET <url>:<port>/api/v1/user`
   * `curl -XPOST <url>:<port>/api/v1/user -H 'Content-Type:application/json' -d '{"username":"Alice"}' `

