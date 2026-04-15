#!/bin/bash

INPUT=$(cat)

# Extract fields from the JSON input
TIMESTAMP=$(date -u +"%Y-%m-%dT%H:%M:%SZ")
TOOL_NAME=$(echo "$INPUT" | jq -r '.toolName // "unknown"')
SESSION_ID=$(echo "$INPUT" | jq -r '.sessionId // "no-session"')
FILE_PATH=$(echo "$INPUT" | jq -r '.toolArgs' | jq -r '.path // "n/a"' 2>/dev/null)

# Create the log directory if it doesn't exist
mkdir -p .github/hooks

# Write a header if the file doesn't exist yet
if [ ! -f .github/hooks/audit.csv ]; then
  echo "timestamp,tool,file,session" > .github/hooks/audit.csv
fi

# Append the log entry
echo "$TIMESTAMP,$TOOL_NAME,$FILE_PATH,$SESSION_ID" >> .github/hooks/audit.csv

# Never block the agent — this is observation only
echo '{"continue": true}'
