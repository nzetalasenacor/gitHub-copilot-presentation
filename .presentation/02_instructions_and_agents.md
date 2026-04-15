# Instructions & agent files

<div style="font-family: system-ui, -apple-system, sans-serif; max-width: 980px; margin: 0 auto; padding: 20px 0;">

<div style="display: flex; gap: 20px; align-items: center; justify-content: center; flex-wrap: nowrap;">

<!-- Left: Sources -->
<div style="flex: 0 1 360px; min-width: 0; display: flex; flex-direction: column; gap: 8px;">
<p style="text-align: center; font-size: 12px; color: #6b6a65; margin: 0 0 4px;">
</p>

<div style="background: #E6F1FB; border: 1px solid #85B7EB; border-radius: 8px; padding: 10px 14px;">
<div style="font-size: 13px; font-weight: 600; color: #0C447C;">copilot-instructions.md</div>
<div style="font-size: 11px; color: #185FA5;">Global: always loaded</div>
</div>

<div style="background: #E1F5EE; border: 1px solid #5DCAA5; border-radius: 8px; padding: 10px 14px;">
<div style="font-size: 13px; font-weight: 600; color: #085041;">*.instructions.md</div>
<div style="font-size: 11px; color: #0F6E56;">Scoped: on pattern match</div>
</div>

<div style="background: #FAECE7; border: 1px solid #F0997B; border-radius: 8px; padding: 10px 14px;">
<div style="font-size: 13px; font-weight: 600; color: #712B13;">*.agent.md</div>
<div style="font-size: 11px; color: #993C1D;">On @agent-name invoke</div>
</div>

<div style="background: #FAEEDA; border: 1px solid #EF9F27; border-radius: 8px; padding: 10px 14px;">
<div style="font-size: 13px; font-weight: 600; color: #633806;">*.prompt.md</div>
<div style="font-size: 11px; color: #854F0B;">On /command invoke</div>
</div>

<div style="background: #EEEDFE; border: 1px solid #AFA9EC; border-radius: 8px; padding: 10px 14px;">
<div style="font-size: 13px; font-weight: 600; color: #3C3489;">AGENTS.md</div>
<div style="font-size: 11px; color: #534AB7;">Multi-tool: always loaded</div>
</div>

<div style="background: #EAF0FF; border: 1px solid #91A7F7; border-radius: 8px; padding: 10px 14px;">
<div style="font-size: 13px; font-weight: 600; color: #2847A1;">SKILL.md</div>
<div style="font-size: 11px; color: #4563C7;">Reusable capability packs: loaded when applicable</div>
</div>

<div style="background: #F6F3FF; border: 1px solid #C7B8F8; border-radius: 8px; padding: 10px 14px;">
<div style="font-size: 13px; font-weight: 600; color: #4C3D91;">Organization instructions</div>
<div style="font-size: 11px; color: #6A57B8;">Shared guardrails across teams</div>
</div>

<div style="height: 1px; background: linear-gradient(to right, transparent, #C4C2B8, transparent); margin: 12px 0;"></div>

<div style="background: #F4ECFF; border: 1px solid #C696F7; border-radius: 8px; padding: 10px 14px;">
<div style="font-size: 13px; font-weight: 600; color: #6E2AA5;">Hooks</div>
<div style="font-size: 11px; color: #8D44C4;">Lifecycle interceptors: adjacent to the stack</div>
</div>
</div>

<div style="flex: 0 0 auto; font-size: 24px; line-height: 1; color: #9c9a92;">→</div>

<!-- Middle: Merge -->
<div style="flex: 0 0 auto; display: flex; flex-direction: column; align-items: center; gap: 8px;">
<div style="background: #F1EFE8; border: 1px dashed #B4B2A9; border-radius: 12px; padding: 20px 16px; text-align: center;">
<div style="font-size: 13px; font-weight: 600; color: #444441;">Copilot loads,<br>merges, and<br>triggers what applies</div>
<div style="font-size: 11px; color: #5F5E5A; margin-top: 8px;">Instructions stack, hooks intercept</div>
</div>
</div>

<div style="flex: 0 0 auto; font-size: 24px; line-height: 1; color: #9c9a92;">→</div>

<!-- Right: Output -->
<div style="flex: 0 1 220px; min-width: 0;">
<div style="background: #EAF3DE; border: 1px solid #97C459; border-radius: 12px; padding: 20px 16px; text-align: center;">
<div style="font-size: 14px; font-weight: 600; color: #27500A;">Context-aware<br>response</div>
<div style="font-size: 11px; color: #3B6D11; margin-top: 8px;">Code, tests, reviews<br>follow your rules</div>
</div>
</div>

</div>
</div>


