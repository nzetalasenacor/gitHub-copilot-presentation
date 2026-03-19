<!--
  A skill is a folder with a SKILL.md file and optional resources (templates,
  scripts, examples). Copilot loads it automatically when it detects a relevant
  task, or you can invoke it manually with /flyway-migration in chat.

  HOW IS THIS DIFFERENT FROM INSTRUCTIONS?
  Instructions = short rules, always loaded (or on file pattern match)
  Skills = detailed workflows with resources, loaded only when relevant

  Use instructions for: "use constructor injection", "never use any"
  Use skills for: "here's exactly how to create a migration, with a template,
  naming rules, validation steps, and examples"

  The folder name MUST match the "name" in the frontmatter below.

  HOW COPILOT USES IT:
  1. Discovery — reads the name and description from frontmatter
  2. Match — when you ask "add a table" or "create a migration", it matches
  3. Load — injects SKILL.md into context
  4. Resources — reads template.sql only when it needs it

  This progressive loading means you can have many skills without bloating context.

  INVOKE MANUALLY:
  /flyway-migration Add a categories table with name and description fields
-->

---
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
