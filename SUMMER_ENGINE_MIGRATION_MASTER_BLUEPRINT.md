# Summer Engine Migration Master Blueprint: Quantum Effect

This master blueprint is designed for **Summer Engine** (a prompt-driven AI engine built on Godot 4). It organizes all design concepts, mechanics, asset specifications, and systems into a clean, scannable document, ready for immediate ingestion and acceleration.

---

## 1. EXECUTIVE GAME SUMMARY

**Quantum Effect** is an HD-2D isometric real-time action RPG styled with a dark, high-contrast cyberpunk and neon visual aesthetic. The game takes place in a dystopian city where players navigate faction-torn streets, upgrade bio-mechanical augmentations, and engage in fast-paced real-time combat. The core loop revolves around taking quests from competing factions (Enlighteners vs. Technopunks), battling security drones and rival enforcers in grid-based environments, and optimizing combat efficiency using custom skill synergies and modular nanite modifications.

---

## 2. ASSET INVENTORY & MAPPING

To construct the scene and populate characters, use the following assets located in the project's workspace:

| Asset Group | File Path / Spec | Summer Engine Application & Scene Mapping Instruction |
| :--- | :--- | :--- |
| **Combat Effects Sheet** | `res://app/src/main/res/drawable/img_combat_effects.jpg` | Use as a texture atlas on `GPUParticles2D` or dynamic `Sprite2D` nodes to animate slashing arcs, plasma rings, lightning bolts, and healing sparkles during real-time combat events. |
| **Isometric Floor Tiles** | 64x32 pixel tiles (2:1 aspect ratio) | Applied to a Godot `TileMap` on Layer 0 to render the isometric cyberpunk streets, neon-lit grates, and concrete platforms. |
| **Cyber-Tech Terminal Nodes** | Interactive Environment Objects | Applied to static body nodes with `CollisionPolygon2D` configured to match isometric boundary coordinates for cyber-hacking terminals. |
| **Hero Character Sheets** | 8-Way Isometric Sheet | Mapped as an animated sprite sheet on a `CharacterBody2D` player node with 8-directional idle, walk, run, and attack animation sequences. |
| **Enemy Security Drones** | Float-Hover Drone Sprites | Applied to a `CharacterBody2D` enemy pathfinder node with hovering offset sin-waves added to their visual position. |
| **Faction Crests & UI Icons** | Vector & Raster UI Elements | Applied to `TextureRect` or custom sub-viewport panels inside the HUD CanvasLayer to visually represent reputation levels and augmentations. |

---

## 3. CORE MECHANICS & LOGIC (TRANSLATED TO ENGLISH)

### A. Isometric 8-Way Movement System
* **Vector Translation**: Input vectors from hardware keyboards (arrows/WASD) or virtual touch joysticks are translated into isometric coordinates using the standard formula:
  * $Screen.X = (World.X - World.Y) \times \cos(30^\circ)$
  * $Screen.Y = (World.X + World.Y) \times \sin(30^\circ)$
* **Collision Handling**: Slide collisions are processed by Godot's native move-and-slide mechanics, maintaining movement velocity along wall boundaries.
* **Frictional Dampening**: When player inputs are released, velocity decreases exponentially to prevent unnatural stops on mobile glass touchscreens.

### B. Combat Mechanics & Damage Calculations
* **Basic Kinetic Damage**: 
  $$\text{Damage} = \text{WeaponPower} \times \text{SkillMultiplier} \times (1 + \text{CyberneticStrengthAugment})$$
* **Critical Hit Chance**: Calculated on impact. A randomized float between $0.0$ and $1.0$ is checked against the player's critical strike attribute. If triggered, damage is multiplied by a critical modifier (e.g., $1.5\times$ to $3.0\times$ depending on whether it is a Light, Medium, Heavy, or Critical tier effect).
* **Impact Visual Density**: On successful hits, particle emission count scales dynamically based on damage:
  * **Light Hit**: Emit 10–20 spark particles.
  * **Medium Hit**: Emit 25–50 sparks and a mini shockwave ring.
  * **Heavy Hit**: Emit 60–100 sparks, expanding glow maps, and a screen shake.
  * **Critical Hit**: Trigger dynamic rock debris bursts and full-intensity flash frames.

### C. Elemental Synergies (The Combo Engine)
* **Application Status**: Skills apply temporary elemental status markers onto enemies. When dual elements overlap, they trigger instantaneous combo detonations:
  * **Fire Tornado** (Fire + Wind): Pulls nearby targets toward the explosion center using a physical force attractor over 1.5 seconds.
  * **Electro Frost** (Electric + Ice): Instantly freezes targets for 2 seconds and releases high-voltage radial arc discharges to chain adjacent enemies.
  * **Magma Spike** (Earth + Fire): Erupts physical rock spikes that block navigation paths and deal thermal damage over time.
  * **Void Nova** (Void + Gravity): Pulls targets inward, collapses into a singularity, and detonates, clearing armor defenses by 30%.

### D. Bio-Mechanical Augmentation Systems
* **Augment Allocation**: Slots represent distinct parts of the character (e.g., Cybernetic Arms, Neural Processors, Nanite Systems).
* **Attribute Binding**: Arm slot upgrades add active modifiers to melee sweep radii and physical weight multipliers. Neural modifications increase elemental synergy trigger chances and reduce skill cool-down cycles.

### E. Faction & Reputation Engine
* **Dynamic Standings**: Completing terminal hacks or neutralizing rival patrols shifts the Enlighteners and Technopunks faction pools in opposite directions.
* **Gameplay Scaling**: Siding with Enlighteners lowers cyber-ware prices but spawns elite Technopunk heavy-weapon bounty hunters in urban zones.

---

## 4. STEP-BY-STEP SUMMER ENGINE ACCELERATION PROMPTS

Use the following three prompts sequentially inside Summer Engine to completely recreate and scaffold this game from scratch.

### PROMPT 1: Map Scaffold, Isometric Grid, and Camera
```text
Create a 2D isometric world map scene in Godot 4.x. 
1. Add a Node2D root named 'World'.
2. Inside 'World', add a TileMapLayer or TileMap node configured with an isometric orientation (64x32 cell size, 2:1 ratio).
3. Use a cyberpunk-themed tileset representing a dark, neon-lit sci-fi street environment with glowing wire ducts, metal panels, and holographic terminals.
4. Add a Camera2D node with smooth camera drag, limits matching the map boundaries, and a subtle zoom-in factor tailored to handheld mobile devices (e.g., Samsung Z Fold 6 or OnePlus 15).
5. Ensure the grid aligns perfectly with isometric movement vectors. Provide fully documented GDScript attached to 'World' that generates a simple grid of walkable neon pathways and solid obstacles.
```

### PROMPT 2: Player Character, 8-way Isometric Movement, and Animations
```text
Implement an 8-way isometric player character scene in Godot 4.x.
1. Create a CharacterBody2D root named 'Player'. Set collision layers to Layer 1 (Player) and mask to Layer 2 (World Obstacles).
2. Add a CollisionShape2D (CapsuleShape2D) positioned horizontally at the player's feet to handle isometric collisions.
3. Add an AnimatedSprite2D and coordinate walk, run, melee, and ranged attack animations facing 8 isometric directions: Up-Right, Right, Down-Right, Down, Down-Left, Left, Up-Left, Up.
4. Attach a GDScript to 'Player' that takes keyboard and mobile virtual touch inputs, translates them into true isometric movement vectors (X down-right, Y down-left), and applies standard kinematic move_and_slide.
5. Code smooth acceleration, deceleration, sprite direction orientation based on movement velocity, and transition triggers for idle, walk, and run states.
```

### PROMPT 3: Combat VFX, Damage Indicators, and Elemental Synergy Engine
```text
Implement the real-time Cyberpunk Combat VFX and Elemental Combo system in Godot 4.x.
1. Create a dynamic combat manager script or node attached to the Player scene.
2. Build a particle trigger method that takes a position, damage amount, and hit tier ("LIGHT", "MEDIUM", "HEAVY", "CRITICAL") and instances a GPUParticles2D or CPUParticles2D effect.
3. Configure the particles using 'res://app/src/main/res/drawable/img_combat_effects.jpg' as the texture atlas, supporting spark emission, glowing rings, and physical debris bursts matching the visual weight of the hit.
4. Code the Elemental Combo Engine: create a status registry on enemies that detects overlapping elements. If 'Fire' and 'Wind' overlap, trigger a 'Fire Tornado' vortex that drags enemies inward. If 'Electric' and 'Ice' overlap, trigger an 'Electro Frost' freeze and chain-lightning discharge.
5. Render glowing floating damage numbers using Label nodes that animate upward, scale based on critical hits, and fade out using a Tween. Make sure all particle allocations are pooled and optimized for mobile processors.
```
