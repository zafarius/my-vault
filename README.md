# my-vault-service
### Short description
An open-source vault designed to store your private images, documents, videos, and more. It features basic authentication and role-based access control for enhanced security.

### Quick start
You can start the application using `docker compose`, which will launch the PostgreSQL database and the my-vault service. To gain a clearer understanding of how the application functions, run the demo sample.
1. ```cd docker/docker-compose/ && docker compose up```
2. ```cd samples/ && ./demo.sh```

### Overview architecture
The architecture employs the port-adapter pattern, also known as hexagonal architecture, to create loosely coupled components. For instance, the domain account focuses solely on the repository interface, rather than the specific implementation details. This allows for easy interchangeability.