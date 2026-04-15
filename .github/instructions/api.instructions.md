---
applyTo: "src/main/java/com/senacor/dev/days/ai_assisted_coding_service/recipe/controller/**"
---

## REST API conventions

- Annotate all controllers with `@RestController` and `@RequestMapping("/api/v1/...")`
- Use `@Valid` on all `@RequestBody` parameters
- Wrap all responses in our standard envelope — see `com.demo.dto.ApiResponse<T>`
- Never put business logic in controllers — delegate to a service
- Log the request at INFO level at the start of every method using the class logger
