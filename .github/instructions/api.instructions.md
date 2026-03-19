---
applyTo: "src/main/java/com/senacor/dev/days/ai_assisted_coding_service/recipe/controller/**"
---

<!--
# API Layer Instructions
#
# HOW TO USE THIS FILE:
# 1. Place in: .github/instructions/api.instructions.md
# 2. The "applyTo" frontmatter below tells Copilot to ONLY use these rules
#    when working on files matching that glob pattern
# 3. You can create as many .instructions.md files as you want — one per layer,
#    one per module, one per concern
# 4. These stack WITH the global copilot-instructions.md — they don't replace it
# 5. The filename can be anything, as long as it ends in .instructions.md
-->

## REST API conventions

- Annotate all controllers with `@RestController` and `@RequestMapping("/api/v1/...")`
- Use `@Valid` on all `@RequestBody` parameters
- Wrap all responses in our standard envelope — see `com.demo.dto.ApiResponse<T>`
- Never put business logic in controllers — delegate to a service
- Log the request at INFO level at the start of every method using the class logger
