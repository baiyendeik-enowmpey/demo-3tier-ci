# Demo 3-Tier CI (Java 17 + Coverage)

![Build Status](https://github.com/baiyendeik-enowmpey/demo-3tier-ci/actions/workflows/ci.yml/badge.svg)
![Coverage](https://img.shields.io/badge/coverage-auto--generated-blue)

Spring Boot Projekt mit moderner 3-Schicht-Architektur, Validierung, Docker und CI/CD Pipeline inklusive JaCoCo Code Coverage.

## Features
- Java 17
- DTOs und Validierung
- @ControllerAdvice Error Handling
- Dockerfile
- GitHub Actions Workflow (Build + Test + Coverage + Docker Build)

## Lokaler Start
```bash
mvn spring-boot:run
```

## Coverage Report
Nach dem Testlauf:
```
target/site/jacoco/index.html
```

## CI/CD Ablauf
1. Checkout Code
2. Setup Java 17
3. Maven Build & Tests
4. JaCoCo Coverage Report
5. Docker Build (nur Test)
