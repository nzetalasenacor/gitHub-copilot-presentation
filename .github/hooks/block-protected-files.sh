#!/bin/bash
# =================================================================
# HOOK: block-protected-files.sh
# EVENT: preToolUse (runs BEFORE the agent edits/creates a file)
#
# WHAT IT DOES:
# Blocks the agent from modifying files in protected directories:
# - db/migration/  → Flyway migrations must never be edited after creation
# - .github/       → CI/CD and hook configs should be changed by humans
# - config/        → Application config is sensitive
#
# HOW IT WORKS:
# 1. Copilot sends JSON via stdin with the tool name and arguments
# 2. This script reads the JSON, extracts the file path
# 3. If the path matches a protected pattern, it returns:
#    {"continue": false, "systemMessage": "..."}
#    This BLOCKS the action and tells the agent WHY
# 4. If the path is safe, it returns:
#    {"continue": true}
#    This ALLOWS the action to proceed
#
# WHY NOT JUST USE AN INSTRUCTION?
# You could write "never edit migration files" in copilot-instructions.md.
# The agent would usually obey. But "usually" isn't good enough for
# database migrations — one bad edit could corrupt production data.
# This hook makes it physically impossible, not just discouraged.
#
# TESTING THIS HOOK LOCALLY:
# echo '{"toolName":"edit","toolArgs":"{\"path\":\"src/main/resources/db/migration/V1__init.sql\"}"}' \
#   | .github/hooks/block-protected-files.sh
# Expected output: {"continue":false,"systemMessage":"..."}
#
# echo '{"toolName":"edit","toolArgs":"{\"path\":\"src/main/java/com/demo/service/RecipeService.java\"}"}' \
#   | .github/hooks/block-protected-files.sh
# Expected output: {"continue":true}
# =================================================================

# Read the JSON that Copilot sends via stdin
INPUT=$(cat)

# Extract the tool name (edit, create, bash, view, etc.)
TOOL_NAME=$(echo "$INPUT" | jq -r '.toolName // empty')

# Only check file-modifying tools — let read-only tools pass through
if [ "$TOOL_NAME" != "edit" ] && [ "$TOOL_NAME" != "create" ]; then
  echo '{"continue": true}'
  exit 0
fi

# Extract the file path from the tool arguments
# toolArgs is a JSON string inside the JSON, so we parse it twice
FILE_PATH=$(echo "$INPUT" | jq -r '.toolArgs' | jq -r '.path // empty')

# If we couldn't extract a path, allow it (don't block blindly)
if [ -z "$FILE_PATH" ]; then
  echo '{"continue": true}'
  exit 0
fi

# --- Protected path rules ---

# Rule 1: Never edit Flyway migrations
if echo "$FILE_PATH" | grep -qE "(db/migration/|flyway/)"; then
  echo "{\"continue\": false, \"systemMessage\": \"BLOCKED: Cannot modify migration file '$FILE_PATH'. Flyway migrations are immutable once created. Create a new migration instead.\"}"
  exit 0
fi

# Rule 2: Never edit CI/CD or hook configurations
if echo "$FILE_PATH" | grep -qE "^\.github/"; then
  echo "{\"continue\": false, \"systemMessage\": \"BLOCKED: Cannot modify CI/CD config '$FILE_PATH'. These files must be changed by a human through a reviewed PR.\"}"
  exit 0
fi

# Rule 3: Never edit application config files
if echo "$FILE_PATH" | grep -qE "(application.*\.yml|application.*\.properties)"; then
  echo "{\"continue\": false, \"systemMessage\": \"BLOCKED: Cannot modify application config '$FILE_PATH'. Configuration changes require human review.\"}"
  exit 0
fi

# Everything else is allowed
echo '{"continue": true}'
