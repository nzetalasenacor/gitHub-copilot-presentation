# AGENTS.md
#
<!-- 
1. Place at the root of your repository
2. Works with Copilot, Claude Code, Gemini CLI, and other AI coding tools
3. Use this for info that ANY agent needs: build commands, project structure, setup steps
-->

## Project overview

This is a Spring Boot 3.5.5 REST API for managing recipes.
Maven, PostgreSQL, Flyway migrations.

## How to build and run

```bash
# Commands below assume Maven is installed locally (`./mvnw` is incomplete in this repo)

# Start PostgreSQL locally
docker compose up -d postgres

# Build (skips tests for speed)
mvn clean package -DskipTests

# Run locally (uses src/main/resources/application.properties)
mvn spring-boot:run

# Run all tests (loads the full Spring context and local PostgreSQL settings)
mvn test
```

## Project structure

```
src/main/java/com/senacor/dev/days/ai_assisted_coding_service/
  common/api/     → Error payloads + centralized exception handling
  config/         → Spring configuration classes
  recipe/
    controller/   → REST endpoints (currently RecipeController for POST /api/recipes)
    domain/       → JPA entity classes (Recipe, Category, Ingredient, Step)
    dto/          → Request/response payload classes with Jackson + validation annotations
    repository/   → Spring Data JPA repositories
    service/      → Business logic and recipe-specific validation
```

## Database

- Flyway migrations are in `src/main/resources/db/migration/`
- Migration naming: `V{number}__{description}.sql` (two underscores)
- Never modify existing migrations — always create a new one
- Local configuration uses PostgreSQL on port 5432, database name `recipes`
- `docker-compose.yml` provisions a matching local PostgreSQL 16 instance (`postgres` service)

## Important files to know about

- `src/main/java/com/senacor/dev/days/ai_assisted_coding_service/common/api/GlobalExceptionHandler.java` → centralized error handling
- `src/main/java/com/senacor/dev/days/ai_assisted_coding_service/config/SecurityConfig.java` → security is currently open (`permitAll`) with CSRF disabled
- `src/main/java/com/senacor/dev/days/ai_assisted_coding_service/recipe/controller/RecipeController.java` → current API entry point for recipe creation
- `src/main/java/com/senacor/dev/days/ai_assisted_coding_service/recipe/service/RecipeService.java` → orchestrates recipe creation, step validation, and response mapping
- `src/main/resources/application.properties` → local dev configuration
