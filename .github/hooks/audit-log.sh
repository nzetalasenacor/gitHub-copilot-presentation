#!/bin/bash
# =================================================================
# HOOK: audit-log.sh
# EVENT: postToolUse (runs AFTER every tool call)
#
# WHAT IT DOES:
# Logs every action the agent takes to a CSV file.
# Each line records: timestamp, tool name, file path, session ID
#
# WHY USE THIS?
# - Compliance: know exactly what the agent did in a session
# - Debugging: when something goes wrong, trace the agent's steps
# - Learning: review logs to improve your instructions and hooks
#
# The log file is written to .github/hooks/audit.csv
# You may want to add this file to .gitignore
#
# This hook never blocks the agent — it's purely observational.
# =================================================================

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
