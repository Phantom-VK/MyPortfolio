# Certifications Section — Design

## Context

The portfolio has an Achievements carousel (coverflow-style, drag/flick/momentum, reduced-motion strip fallback, empty state) added during the matrix-elevated redesign. It shows felicitations and awards. The user wants a second, visually identical section for top certifications, populated later with PNG images of each certificate.

Branch: `redesign/matrix-elevated`. This work lands there; the branch (including this feature) will later be merged into `master`.

## Goals

- A Certifications section that looks and behaves exactly like Achievements (same physics, accessibility, empty state pattern).
- Zero duplication of the carousel engine.
- Ships today with zero content — user adds PNGs and entries later without touching code.

## Non-goals

- No PDF rendering support. All certificates are supplied as PNG images.
- No verification-link rendering. The existing `href` field on the shared item type stays present but unused, as it already is for Achievements.
- No new nav item / scroll-spy entry for Certifications, consistent with most sections (Experience, What I Do, Tech Stack have none either).

## Design

### 1. Rename the shared data type

`utils/Achievement.kt` → `utils/Recognition.kt`. The `Achievement` data class becomes `Recognition`, fields unchanged (`id`, `title`, `issuer`, `year`, `caption`, `imageUrl`, `imageAlt`, `href`, `intrinsicWidth`, `intrinsicHeight`). It was already domain-agnostic apart from its name and file location.

`AchievementsData` (existing felicitation entries) stays in `Recognition.kt`.

### 2. New data object for certifications

`utils/CertificationsData.kt`:

```kotlin
object CertificationsData {
    val items: List<Recognition> = listOf(
        // populated later
    )
}
```

Starts empty; the carousel's existing empty state handles that with zero code changes.

### 3. Generalize the carousel component

`components/AchievementCoverflow.kt` → rename composable `AchievementCoverflow` to `RecognitionCoverflow`. Add two parameters:

```kotlin
fun RecognitionCoverflow(
    items: List<Recognition>,
    modifier: Modifier = Modifier,
    label: String = "Achievements",
    emptyTitle: String,
    emptySubtitle: String,
)
```

`CoverflowEmpty` uses `emptyTitle`/`emptySubtitle` instead of the hardcoded "Felicitations" / "Awards and recognitions are being added here." strings. All other internals (`CoverflowPhysics`, `AchievementStrip`, `CoverflowCardFace`, drag/momentum/spring physics, ARIA wiring) are unchanged — this is a signature-only generalization, no behavioral change for the Achievements caller.

The file is renamed to `RecognitionCoverflow.kt` to match, since it no longer only serves achievements.

### 4. New section

`sections/CertificationsSection.kt`, mirroring `AchievementsSection.kt`:

```kotlin
@Composable
fun CertificationsSection() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(leftRight = Space.lg, topBottom = Section.gapSm),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RecognitionCoverflow(
            items = CertificationsData.items,
            label = "Certifications",
            emptyTitle = "Certifications",
            emptySubtitle = "Top certifications are being added here.",
        )
    }
}
```

`AchievementsSection.kt` updates its call site to pass the same `emptyTitle`/`emptySubtitle` it already renders today ("Felicitations" / "Awards and recognitions are being added here."), preserving current behavior exactly.

### 5. Page wiring

`pages/Index.kt`: one new `RevealedSection` line immediately after Achievements, same shape:

```kotlin
RevealedSection("Achievements", id = "achievements", animateBody = false) { AchievementsSection() }
RevealedSection("Certifications", id = "certifications", animateBody = false) { CertificationsSection() }
```

No changes to `Header.kt` nav items or the scroll-spy `NAV_SECTIONS` list.

### 6. Assets

New empty folder: `site/src/jsMain/resources/public/images/certifications/`. Card box is a fixed 16:10 `object-fit: cover`, same as Achievements — landscape-ish PNGs crop well, tall certificate scans will crop hard (same caveat already true for achievements imagery).

## Files touched

| File | Change |
|---|---|
| `utils/Achievement.kt` | renamed to `utils/Recognition.kt`; `Achievement` → `Recognition` |
| `utils/CertificationsData.kt` | new, empty `items` list |
| `components/AchievementCoverflow.kt` | renamed to `components/RecognitionCoverflow.kt`; composable renamed, `emptyTitle`/`emptySubtitle` params added, all `Achievement` type references become `Recognition` |
| `sections/AchievementsSection.kt` | updated call site (new params, `Recognition` type refs) |
| `sections/CertificationsSection.kt` | new |
| `pages/Index.kt` | one new `RevealedSection` line |
| `resources/public/images/certifications/` | new empty folder |

Not touched: `styles/CoverflowStyle.kt`, `components/CoverflowController.kt`, `sections/Header.kt`.

## Verification

- `kobweb run`, confirm Achievements renders identically to before (same empty-state copy, same physics).
- Confirm Certifications renders its own empty state directly below Achievements.
- `kobweb export --layout static` still succeeds.
- Grep for lingering `Achievement` / `AchievementCoverflow` references to confirm the rename is complete everywhere.

## Sequencing with the git task

This work happens on `redesign/matrix-elevated`. Once implemented and verified, the entire branch (this change plus the prior redesign commits already on it) gets merged into `master` and pushed — a separate step, not part of this spec.
