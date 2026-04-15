# Copilot Instructions

## Stack
- Maven for builds (`mvn clean install`)
- PostgreSQL database with Spring Data JPA
- Flyway for database migrations

## Code style
- Use constructor injection, never field injection with @Autowired
- All REST controllers go in `src/main/java/com/demo/controller/`
- All services go in `src/main/java/com/demo/service/`
- Use `record` types for DTOs, never mutable classes
- Return `ResponseEntity<>` from all controller methods
- Never catch generic `Exception` — catch specific types

## Naming
- Controllers: `XxxController.java`
- Services: `XxxService.java` (interface) + `XxxServiceImpl.java`
- Repositories: `XxxRepository.java`
- DTOs: `XxxRequest.java`, `XxxResponse.java`

## Testing
- Write tests with JUnit 5 and Mockito
- Use `@WebMvcTest` for controller tests, `@DataJpaTest` for repository tests
- Test names follow: `shouldDoSomething_whenCondition`
- Every new public method needs at least one test
