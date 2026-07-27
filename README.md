<div align="center">
<img width="1200" height="475" alt="GHBanner" src="https://ai.google.dev/static/site-assets/images/share-ais-513315318.png" />
</div>

# Quantum Effect

An HD-2D isometric real-time action RPG with a dark, high-contrast cyberpunk and
neon aesthetic. Players navigate faction-torn streets, upgrade bio-mechanical
augmentations, and fight in grid-based environments while optimizing skill
synergies and modular nanite modifications.

The full design — mechanics, damage formulas, elemental combos, asset mapping —
lives in [`SUMMER_ENGINE_MIGRATION_MASTER_BLUEPRINT.md`](SUMMER_ENGINE_MIGRATION_MASTER_BLUEPRINT.md).

View the app in AI Studio: https://ai.studio/apps/d0590df4-c60f-4b93-afe8-41bd4ef79aad

## Run Locally

**Prerequisites:** [Android Studio](https://developer.android.com/studio)

1. Open Android Studio.
2. Select **Open** and choose the directory containing this project.
3. Allow Android Studio to fix any incompatibilities as it imports the project.
4. Provide a Gemini API key by any one of:
   - creating a `.env` file in the project root with `GEMINI_API_KEY=...` (see `.env.example`),
   - exporting `GEMINI_API_KEY` in your environment, or
   - passing `-PgeminiApiKey=...` to Gradle.

   The build folds whichever it finds into `BuildConfig.GEMINI_API_KEY`. Without a
   key the app still builds and runs; the AI terminals report that no key is
   configured instead of failing.
5. Run the app on an emulator or physical device.

## Project layout

```
app/src/main/java/com/example/
├── MainActivity.kt              Compose entry point and dashboard shell
├── game/
│   ├── engine/                  Pure-Kotlin rules engine (no Android deps, unit tested)
│   │   ├── CombatMath.kt        Damage formula, crit tiers, particle budgets
│   │   ├── ElementalSynergy.kt  Element markers and combo detonations
│   │   ├── AugmentBinding.kt    Augment loadout → derived combat attributes
│   │   ├── FactionReputationEngine.kt  Opposed-axis standings, pricing, bounties
│   │   └── IsometricMath.kt     Iso projection, 8-way facing, friction dampening
│   ├── models/                  Game data classes and enums
│   ├── db/                      Room entity, DAO, database and repository
│   ├── api/                     Gemini REST DTOs, Retrofit service, helpers
│   ├── viewmodel/               GameViewModel — all game state and actions
│   └── ui/                      Compose screens
└── ui/theme/                    Neon color palette and Material theme
```

Everything under `game/engine/` is deliberately free of Android and Compose
imports so the rules can be exercised on the JVM:

```
./gradlew :app:testDebugUnitTest
```

## Notes on the rules engine

The engine implements the blueprint directly, so the numbers in the design
document are the numbers in the code:

- **Damage** is `WeaponPower × SkillMultiplier × (1 + CyberneticStrengthAugment)`,
  with defense subtracted afterwards and armor pierce applied to defense first.
- **Hit tiers** (Light / Medium / Heavy / Critical) are classified by the fraction
  of the target's health bar removed, and drive both the critical multiplier
  (1.5× → 3.0×) and the impact particle budget (10–20 → 120–200).
- **Elemental combos** detonate when two markers overlap on a target: Fire + Wind
  → Fire Tornado, Electric + Ice → Electro Frost, Earth + Fire → Magma Spike,
  Void + Gravity → Void Nova. Both markers are consumed, so one application
  cannot chain twice.
- **Reputation** moves on an opposed axis — gaining standing with the Aurelian
  Order costs you half as much with the Emberpact — and feeds cyber-ware pricing
  and elite bounty-hunter spawn chance.
