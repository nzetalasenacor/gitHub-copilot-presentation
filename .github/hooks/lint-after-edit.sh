#!/bin/bash
# =================================================================
# HOOK: lint-after-edit.sh
# EVENT: postToolUse (runs AFTER the agent edits/creates a file)
#
# WHAT IT DOES:
# Runs the code formatter (Spotless) on any Java file the agent
# just edited. This ensures every file the agent touches is
# properly formatted — no matter what.
#
# WHY NOT JUST USE AN INSTRUCTION?
# You could write "always format your code" in copilot-instructions.md.
# But the agent formats at the END of a task, if it remembers.
# This hook formats EVERY file, EVERY time, immediately after edit.
# The result: zero formatting drift, zero extra tokens spent
# asking the agent to format, and the agent never has to think
# about formatting at all.
#
# PERFORMANCE NOTE:
# This runs on a SINGLE file, not the whole project.
# Typical execution: <2 seconds per file.
# If your formatter is slow, increase timeoutSec in the JSON config.
#
# WHAT IF THE FORMATTER FAILS?
# We return {"continue": true} regardless. A formatter failure
# should not stop the agent's work — it's a nice-to-have, not
# a gate. If you want hard enforcement, return continue:false.
# =================================================================

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
