# cloud-itonami-isco-2422

Open Occupation Blueprint for **ISCO-08 2422**: Policy Administration Professionals.

This repository designs a forkable OSS business for an independent policy administration professional: a document-handling robot performs physical filing and archive retrieval under a governor-gated actor, so the practice keeps its own drafting and disclosure records instead of renting a closed policy-management SaaS.

## Robotics premise

All cloud-itonami verticals are designed on the premise that a **robot performs
the physical domain work**. Here a document-handling robot performs physical filing, archive retrieval and document scanning under an actor that proposes
actions and an independent **Policy Administration Governor** that gates them. The governor never
dispatches hardware itself; `:high`/`:safety-critical` actions (such as
publishing a policy affecting client legal standing, or disclosure of confidential draft policy) require human sign-off.

A live sample of the operator console (robotics safety console, shared template) is rendered in [docs/samples/operator-console.html](docs/samples/operator-console.html) — pure-data HTML output of `kotoba.robotics.ui`.

## Core Contract

```text
client mandate + policy scope + review protocol
        |
        v
Policy Advisor -> Policy Administration Governor -> draft/publish, or human sign-off
        |
        v
robot actions (gated) + operating records + audit ledger
```

No automated advice can dispatch a robot action the governor refuses, suppress
an operating record, or disclose sensitive data without governor approval and
audit evidence.

## Capability layer

Resolves via [`kotoba-lang/occupation`](https://github.com/kotoba-lang/occupation)
(ISCO-08 `2422`). Required capabilities:

- :robotics
- :identity
- :forms
- :dmn
- :bpmn
- :audit-ledger

See [`docs/business-model.md`](docs/business-model.md) and
[`docs/operator-guide.md`](docs/operator-guide.md).

## License

AGPL-3.0-or-later.
