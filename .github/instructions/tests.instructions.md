---
applyTo: "**/*Test.java"
---

## Testing conventions

- Use `@SpringBootTest` only for integration tests, never for unit tests
- Mock dependencies with `@MockBean` or `Mockito.mock()` — never call real services
- Structure every test with: // given / // when / // then comments
- Use AssertJ assertions (`assertThat(...)`) instead of JUnit's `assertEquals`
- Test both the happy path and at least one error case per method
