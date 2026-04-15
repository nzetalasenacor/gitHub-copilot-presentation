---
mode: agent
description: "Implement a user story following our PRD and technical design"
tools: ['read_file', 'insert_edit_into_file', 'replace_string_in_file', 'run_in_terminal', 'get_errors', 'create_file', 'open_file']
---

<!--
# Implement Story Prompt
1. Place in: .github/prompts/implement-story.prompt.md
2. This defines a reusable prompt
3. The prompt body tells the agent which project documents to read first
-->

Read the requirements from #file:docs/prd-requirements.md
Read the technical approach from #file:docs/technical-solution.md
Read the story map from #file:docs/story-map.md

Now implement the following story based on these documents.
Follow the architecture decisions in the technical solution.
Make sure the implementation satisfies the acceptance criteria in the PRD.