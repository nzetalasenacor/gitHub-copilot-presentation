# AGENTS.md

## Project overview

Recipe Manager — a Spring Boot 3.5.5 REST API for creating, browsing, and searching recipes.  
Java 21 · Maven · PostgreSQL 16 · Flyway · Spring Data JPA · Lombok · Spring Security (open, no auth).

Full requirements: [`docs/prd-requirements.md`](docs/prd-requirements.md).

---

## Build, run, and test

> **Important:** Use `mvn`, not `./mvnw` — the Maven wrapper in this repo is incomplete.

```bash
# Start the local PostgreSQL container (required before anything else)
docker compose up -d postgres

# Build (skip tests for speed)
mvn clean package -DskipTests

# Run the app (port 8080)
mvn spring-boot:run

# Run all tests
mvn test

# Run a single test class
mvn test -Dtest=RecipeControllerTest

# Full verification (compile + test + package)
mvn clean verify
```

### Verifying your changes

After making code changes, always run in this order:

1. `mvn compile` — catch syntax and type errors fast
2. `mvn test` — ensure nothing is broken
3. If you added a Flyway migration, start the app (`mvn spring-boot:run`) to confirm Hibernate validation passes

---

## Project structure

```
src/main/java/com/senacor/dev/days/ai_assisted_coding_service/
├── AiAssistedCodingServiceApplication.java   → Entry point
├── common/api/
│   ├── ApiResponse.java                      → Envelope: { data, error }
│   ├── ErrorDetail.java                      → Error payload (code + message)
│   └── GlobalExceptionHandler.java           → @RestControllerAdvice — all exception mapping
├── config/
│   └── SecurityConfig.java                   → permitAll, CSRF disabled
└── recipe/
    ├── controller/RecipeController.java      → REST endpoints
    ├── domain/                               → JPA entities (Recipe, Category, Ingredient, Step)
    ├── dto/                                  → Request/Response DTOs with validation
    ├── repository/                           → Spring Data JPA interfaces
    └── service/
        ├── RecipeService.java                → Business logic, entity ↔ DTO mapping
        ├── RecipeNotFoundException.java      → 404
        └── RecipeValidationException.java    → 400
```

---

## Database & migrations

- Flyway auto-runs on startup. Migrations live in `src/main/resources/db/migration/`.
- **Naming:** `V{number}__{description}.sql` (two underscores). Next file: `V002__*.sql`.
- **Never modify an existing migration.** Always create a new one with the next sequence number.
- **`ddl-auto=validate`** — Hibernate checks entities match the schema but never alters it.

### Schema change workflow

When adding or changing a database column:

1. Create a new migration file (`V{N+1}__description.sql`)
2. Update the JPA entity in `recipe/domain/`
3. Update the DTOs in `recipe/dto/` if the field is exposed via the API
4. Update the service mapping logic in `RecipeService.java`
5. Run `mvn compile` then `mvn spring-boot:run` to verify Hibernate validation passes

### Current tables

| Table         | PK              | Key relationships                                  |
|---------------|-----------------|-----------------------------------------------------|
| `categories`  | `category_id`   | Referenced by `recipes.category_id`                 |
| `recipes`     | `recipe_id`     | FK to `categories`; parent of ingredients and steps |
| `ingredients` | `ingredient_id` | FK `recipe_id` → `recipes` (CASCADE DELETE)         |
| `steps`       | `step_id`       | FK `recipe_id` → `recipes` (CASCADE DELETE); UNIQUE (`recipe_id`, `position`) |

---

## API conventions

- Base path: `/api/v1/recipes`
- **Envelope format — all responses:**
  - Success: `{ "data": <T>, "error": null }`
  - Failure: `{ "data": null, "error": { "code": "...", "message": "..." } }`
- Error codes: `VALIDATION_ERROR` (400), `RECIPE_NOT_FOUND` (404), `INTERNAL_SERVER_ERROR` (500)
- Never expose stack traces in error responses.

### Implemented endpoints

| Method | Path                   | Status | Description      |
|--------|------------------------|--------|------------------|
| POST   | `/api/v1/recipes`      | 201    | Create a recipe  |
| GET    | `/api/v1/recipes/{id}` | 200    | Get recipe by ID |

### Not yet implemented (see PRD)

- `GET /api/v1/recipes` — List recipes (paginated)
- `PUT /api/v1/recipes/{id}` — Update a recipe
- `DELETE /api/v1/recipes/{id}` — Delete a recipe
- `GET /api/v1/recipes/search?q={keyword}` — Search recipes

---

## Code conventions

### Architecture

- **Controller → Service → Repository** layering. Controllers never call repositories directly.
- Controllers return `ResponseEntity<ApiResponse<T>>`.
- Exception handling is centralized in `GlobalExceptionHandler`. Do **not** add try/catch in controllers.

### Dependency injection

- **Constructor injection** via Lombok `@RequiredArgsConstructor`. Never use `@Autowired` on fields.

### DTOs

- Separate classes per direction: `XxxRequest.java` (inbound), `XxxResponse.java` (outbound).
- Existing DTOs use Lombok `@Getter/@Setter/@Builder`. **Follow this pattern** for consistency when editing existing DTOs.
- Use `@JsonProperty("snake_case")` for snake_case JSON ↔ camelCase Java (e.g. `@JsonProperty("preparation_time_min")`).
- Put validation annotations on request DTO fields (`@NotBlank`, `@NotEmpty`, `@Size`, `@Positive`).
- Add `@Valid` on nested DTO collections (e.g. `@Valid List<StepRequest> steps`).

### Entities

- Live in `recipe/domain/`. Use Lombok `@Getter/@Setter/@NoArgsConstructor/@AllArgsConstructor`.
- Map with `@Table`, `@Column`. Use `CascadeType.ALL` + `orphanRemoval = true` on parent-side `@OneToMany`.

### Naming

| Concept      | Convention                  | Example                    |
|--------------|-----------------------------|----------------------------|
| Controller   | `XxxController.java`        | `RecipeController`         |
| Service      | `XxxService.java`           | `RecipeService`            |
| Repository   | `XxxRepository.java`        | `RecipeRepository`         |
| Entity       | Singular noun               | `Recipe`, `Step`           |
| DTO (in)     | `XxxRequest.java`           | `RecipeRequest`            |
| DTO (out)    | `XxxResponse.java`          | `RecipeResponse`           |
| Exception    | `XxxException.java`         | `RecipeNotFoundException`  |
| Migration    | `V{NNN}__{description}.sql` | `V002__add_servings.sql`   |

### Logging

- Use Lombok `@Slf4j`. Log at `info` on entry to controller methods.

---

## Testing conventions

- **JUnit 5 + Mockito** (via `spring-boot-starter-test`)
- **Controller tests:** `@WebMvcTest(XxxController.class)` — mock the service layer
- **Service tests:** `@ExtendWith(MockitoExtension.class)` — plain unit tests
- **Repository tests:** `@DataJpaTest`
- **Method naming:** `shouldDoSomething_whenCondition()`
- Every new public method must have at least one test
- `spring-security-test` is on the classpath for security-aware MockMvc tests

---

## Common pitfalls

1. **PostgreSQL must be running** — run `docker compose up -d postgres` before building, testing, or starting the app.
2. **Never edit an existing Flyway migration** — Flyway checksums will fail. Always create `V{N+1}__*.sql`.
3. **Migration before entity** — because `ddl-auto=validate`, add the migration first, then update the JPA entity. Reversing this order breaks startup.
4. **Missing `@Valid` on nested DTOs** — `List<StepRequest>` won't be validated without `@Valid` on the field.
5. **Snake_case in JSON** — always add `@JsonProperty("snake_case")` on DTO fields to match the API contract.
