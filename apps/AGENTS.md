# Application Composition Scope

`apps/*` are composition roots.

- Wire concrete platform/runtime implementations into shared services.
- Keep reusable game/protocol/cache/render/plugin logic out of apps.
- Native-only and browser-only startup code is expected here.
- Feature flags selected here must not create different semantic rules for the same server profile.
- Startup failures should identify configuration/profile/platform cause clearly.
