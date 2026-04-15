#!/bin/bash

INPUT=$(cat)

# Only act on file-modifying tools
TOOL_NAME=$(echo "$INPUT" | jq -r '.toolName // empty')

if [ "$TOOL_NAME" != "edit" ] && [ "$TOOL_NAME" != "create" ]; then
  echo '{"continue": true}'
  exit 0
fi

# Extract the file path
FILE_PATH=$(echo "$INPUT" | jq -r '.toolArgs' | jq -r '.path // empty')

if [ -z "$FILE_PATH" ]; then
  echo '{"continue": true}'
  exit 0
fi

# Only format Java files
if echo "$FILE_PATH" | grep -qE "\.java$"; then

  # Run Spotless on just this file (fast, single-file operation)
  # Redirect output to /dev/null — the agent doesn't need to see it
  # If you use a different formatter, replace this command:
  #   Prettier:  npx prettier --write "$FILE_PATH"
  #   Black:     python -m black "$FILE_PATH"
  #   gofmt:     gofmt -w "$FILE_PATH"
  mvn spotless:apply -DspotlessFiles="$FILE_PATH" -q 2>/dev/null

  # If Spotless isn't set up, fall back to google-java-format
  if [ $? -ne 0 ]; then
    # Silently skip — don't block the agent over a formatter issue
    true
  fi
fi

# Always allow the agent to continue
echo '{"continue": true}'
