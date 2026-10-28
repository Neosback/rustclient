# Documentation Agent Scope

This directory owns normative specs, ADRs, diagrams, testing contracts, and developer-facing architecture documentation.

- Specs define required behavior and acceptance criteria.
- ADRs explain architectural decisions and rejected alternatives.
- Keep implementation detail out of specs unless it is part of the compatibility contract.
- Every spec must declare status, owners/subsystems, invariants, non-goals, failure behavior, and acceptance criteria.
- When code and a ratified spec disagree, do not silently rewrite the spec to match code. Reconcile deliberately.
- Prefer links to canonical definitions instead of copying tables across documents.
- Read `docs/specs/SPEC-0019-port-parity.md` and `docs/plan/PORTING-ROADMAP.md`
  for the current parity contract and execution order. `docs/README.md` classifies
  current owners versus historical checkpoints; historical “next” instructions
  do not override the roadmap or current user authorization.
- Update the canonical domain/method/packet ledgers with implementation evidence.
  Do not create another competing current plan, equate consumed bytes with
  semantic support, or carry historical validation forward to a new baseline.
