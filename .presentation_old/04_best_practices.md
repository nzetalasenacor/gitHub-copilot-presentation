# GitHub Copilot Best Practices

## 1. Start with copilot-instructions.md

- Keep it 15–30 lines — long files dilute important rules
- Be specific: "use constructor injection" works, "write clean code" doesn't
- Include build, test, and lint commands so the agent can verify its own work
- Reference key files the agent should follow as patterns
- Test your rules: ask the agent to violate one — if it complies, rewrite the rule

## 2. Scope instructions to reduce noise

- Create layer-specific rules with `applyTo` globs
- The agent only loads scoped files when working on matching files — less noise, better results


## 3. Write prompts like specs, not wishes

- Be specific: include endpoints, field names, status codes, validation rules
- Break large tasks into small, focused requests

## 4. Save repeating prompts as prompt files

- For repeated requests. e.g. "Add a new REST endpoint"

## 5. Create focused agents with clear boundaries

- One agent per role: @test-agent, @security-agent, @docs-agent
- Be explicit about what the agent CANNOT do — boundaries matter more than capabilities
- Set the `model` property per agent — powerful models for planning, fast ones for routine tasks


## 6. Use skills for complex repeatable procedures

- Include templates, scripts, and examples alongside the SKILL.md
- Skills load progressively — they don't bloat the context window
- Good candidates: migrations, scaffolding, CI debugging, deployment checklists


## 7. Protect what matters with hooks

- Instructions are advice, hooks are enforcement — use hooks when drift is dangerous
- Don't over-engineer — add hooks only when instructions fail


## 8. Review everything

- The coding agent creates draft PRs — always review before merging
- Don't accept code you don't understand — ask Copilot to explain it


## 9. Improve continuously

- Agent uses wrong pattern → update instructions
- Agent edits a protected file → add a preToolUse hook
- Agent repeats the same mistake → let Memory capture the convention, or add it to AGENTS.md
- Same prompt typed every time → save as a .prompt.md
- Complex procedure done often → package as a skill

## Sources

- [Best practices — GitHub Docs](https://docs.github.com/en/copilot/get-started/best-practices)
- [Best practices for tasks — GitHub Docs](https://docs.github.com/copilot/how-tos/agents/copilot-coding-agent/best-practices-for-using-copilot-to-work-on-tasks)
- [CLI best practices — GitHub Docs](https://docs.github.com/en/copilot/how-tos/copilot-cli/cli-best-practices)
- [Maximize agentic capabilities — GitHub Blog](https://github.blog/ai-and-ml/github-copilot/how-to-maximize-github-copilots-agentic-capabilities/)
- [Custom instructions — GitHub Docs](https://docs.github.com/copilot/customizing-copilot/adding-custom-instructions-for-github-copilot)
- [Agent skills — GitHub Docs](https://docs.github.com/en/copilot/concepts/agents/about-agent-skills)
- [Hooks — GitHub Docs](https://docs.github.com/en/copilot/concepts/agents/coding-agent/about-hooks)
- [Copilot Memory — GitHub Blog](https://github.blog/ai-and-ml/github-copilot/building-an-agentic-memory-system-for-github-copilot/)
- [Awesome Copilot](https://github.com/github/awesome-copilot)