# Renderer Scope

The renderer owns disposable visual projection state and GPU resources.

- It never becomes authoritative for entity position, inventory, vars, widgets, or protocol state.
- Renderer updates consume committed state/deltas/assets.
- Device/resource loss must be recoverable by rebuild.
- Keep backend-specific code behind renderer/platform boundaries.
- Avoid per-frame allocations and CPU/GPU synchronization without measurement.
- Visual optimizations require parity/regression fixtures where they can change semantics.
- External plugins submit overlay commands; they do not receive `wgpu` handles.
