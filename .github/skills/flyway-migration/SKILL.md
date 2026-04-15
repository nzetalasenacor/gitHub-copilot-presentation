name: flyway-migration
description: >
Create Flyway database migrations for the Spring Boot recipe API.
Use when asked to add a table, modify a column, add an index,
or make any database schema change.
---

# Flyway migration skill

## When to use
- Adding a new table
- Adding or modifying columns
- Creating indexes
- Inserting seed data
- Any schema change

## Where migrations live
`src/main/resources/db/migration/`

## Naming convention
- Format: `V{next_number}__{description}.sql`
- Two underscores between number and description
- Use snake_case for the description
- Check existing files to determine the next number
- Examples: `V1__create_recipe.sql`, `V2__add_category_column.sql`

## Rules
- NEVER modify an existing migration — always create a new one
- Use `IF NOT EXISTS` for tables and indexes
- Always add `created_at` and `updated_at` to new tables
- Include a rollback comment at the top of every file
- Keep each migration focused on one change

## Template
Use this for every new migration:

See [full template](./template.sql) for a complete example.

## After creating a migration
Run validation:
```bash
mvn flyway:validate
```

Run the migration locally:
```bash
mvn flyway:migrate -Dspring.profiles.active=local
```
