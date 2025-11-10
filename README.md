# Demo 3-Tier CI (Java 17)

Spring Boot Projekt mit moderner 3-Schicht-Architektur, Validierung und CI/CD-Pipeline.

## Features
- Java 17
- DTOs und Validierung
- @ControllerAdvice Error Handling
- Dockerfile
- GitHub Actions Workflow (Build + Test + Docker Build)

## Start lokal
```bash
mvn spring-boot:run
```

## CI/CD
Automatischer Build bei jedem Push oder Pull Request auf den `main`-Branch.
Der Workflow:
1. Checkt Code aus
2. Setzt Java 17 auf
3. Führt Maven Build + Tests aus
4. Baut Docker-Image (kein Push)
