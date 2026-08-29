# Certifications Section Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Add a Certifications carousel section, visually and behaviorally identical to the existing Achievements carousel, by generalizing the shared component instead of duplicating it.

**Architecture:** Rename the achievement-specific `Achievement` data class and `AchievementCoverflow` composable to generic `Recognition` / `RecognitionCoverflow` names, parameterizing the two hardcoded empty-state strings. Both `AchievementsSection` and the new `CertificationsSection` call the same generalized composable with their own data and copy. No changes to the physics engine (`CoverflowController.kt`), styles (`CoverflowStyle.kt`), or nav (`Header.kt`).

**Tech Stack:** Kobweb 0.24.0 / Kotlin Compose HTML (jsMain), Gradle, ImageMagick `convert` for image prep.

## Global Constraints

- Branch: all work happens on `redesign/matrix-elevated` (already checked out). Do not merge to `master` as part of this plan — that is a separate follow-up.
- No issuer/year/caption fields beyond `title` and a short `caption` for certification entries (per spec: "No need to add any captions as we did for the achievements" — meaning no long biographical captions, not no secondary line; the user-supplied bracketed text below is the short caption).
- Certification images live at `site/src/jsMain/resources/public/images/certifications/*.jpg`, already added by the user. They must be resized/compressed before use (confirmed with user): max width 1600px, JPEG quality 82, overwritten in place (no separate raw copies are kept elsewhere in this repo's convention).
- No new nav item, no scroll-spy entry, no verification-link (`href`) rendering — confirmed out of scope.
- Verify with `./gradlew :site:compileKotlinJs` after each Kotlin-touching task (this codebase has no unit tests for UI composables; compilation plus a final manual `kobweb run` + `kobweb export --layout static` is the established verification pattern here).

---

### Task 1: Resize and compress the certification images

**Files:**
- Modify (in place, overwrite): `site/src/jsMain/resources/public/images/certifications/agenticai.jpg`
- Modify (in place, overwrite): `site/src/jsMain/resources/public/images/certifications/aiml.jpg`
- Modify (in place, overwrite): `site/src/jsMain/resources/public/images/certifications/android.jpg`
- Modify (in place, overwrite): `site/src/jsMain/resources/public/images/certifications/ociaifoundation.jpg`
- Modify (in place, overwrite): `site/src/jsMain/resources/public/images/certifications/ocids.jpg`
- Modify (in place, overwrite): `site/src/jsMain/resources/public/images/certifications/ocigenai.jpg`

**Interfaces:**
- Produces: 6 resized JPGs at these exact final dimensions (verified in a dry run, so Task 3's code can hardcode `intrinsicWidth`/`intrinsicHeight` without guessing):
  - `agenticai.jpg`: 1600x1190
  - `aiml.jpg`: 1600x1190
  - `android.jpg`: 1600x1190
  - `ociaifoundation.jpg`: 1600x1236
  - `ocids.jpg`: 1600x1236
  - `ocigenai.jpg`: 1600x1236

- [ ] **Step 1: Resize each image to a temp file, then replace the original**

```bash
cd site/src/jsMain/resources/public/images/certifications
for f in agenticai.jpg aiml.jpg android.jpg ociaifoundation.jpg ocids.jpg ocigenai.jpg; do
  convert "$f" -resize 1600x -quality 82 "$f.tmp" && mv "$f.tmp" "$f"
done
```

- [ ] **Step 2: Verify dimensions and file sizes match expectations**

Run: `for f in *.jpg; do identify -format "%f: %wx%h, " "$f"; stat -c%s "$f"; done`

Expected output (byte counts may vary slightly by ImageMagick version, dimensions must match exactly):
```
agenticai.jpg: 1600x1190, ~91935
aiml.jpg: 1600x1190, ~95924
android.jpg: 1600x1190, ~95397
ociaifoundation.jpg: 1600x1236, ~228936
ocids.jpg: 1600x1236, ~231367
ocigenai.jpg: 1600x1236, ~231291
```

If any dimensions differ from the 1600x1190 / 1600x1236 values above, update the `intrinsicWidth`/`intrinsicHeight` values used in Task 3 to match the actual output before proceeding.

- [ ] **Step 3: Commit**

```bash
git add site/src/jsMain/resources/public/images/certifications/
git commit -m "Resize and compress certification images for web"
```

---

### Task 2: Generalize Achievement -> Recognition and AchievementCoverflow -> RecognitionCoverflow

**Files:**
- Create: `site/src/jsMain/kotlin/com/vikramaditya/portfolio/utils/Recognition.kt`
- Delete: `site/src/jsMain/kotlin/com/vikramaditya/portfolio/utils/Achievement.kt`
- Create: `site/src/jsMain/kotlin/com/vikramaditya/portfolio/components/RecognitionCoverflow.kt`
- Delete: `site/src/jsMain/kotlin/com/vikramaditya/portfolio/components/AchievementCoverflow.kt`
- Modify: `site/src/jsMain/kotlin/com/vikramaditya/portfolio/sections/AchievementsSection.kt`

**Interfaces:**
- Produces:
  - `data class Recognition(id: String, title: String, issuer: String? = null, year: String? = null, caption: String = "", imageUrl: String? = null, imageAlt: String = "", href: String? = null, intrinsicWidth: Int? = null, intrinsicHeight: Int? = null)` in package `com.vikramaditya.portfolio.utils`
  - `object AchievementsData { val items: List<Recognition> }` in the same file, unchanged content
  - `@Composable fun RecognitionCoverflow(items: List<Recognition>, modifier: Modifier = Modifier, label: String = "Achievements", emptyTitle: String, emptySubtitle: String)` in package `com.vikramaditya.portfolio.components`
- Consumes: nothing new (this task only renames/generalizes existing code)

- [ ] **Step 1: Create `utils/Recognition.kt` with the renamed type**

```kotlin
package com.vikramaditya.portfolio.utils

/**
 * One felicitation, award, certification, or recognition.
 *
 * Only [id] and [title] are required, so entries can be added now and their
 * images supplied later without touching the carousel. A null [imageUrl] renders
 * a designed typographic face rather than a broken image.
 */
data class Recognition(
    val id: String,
    val title: String,
    val issuer: String? = null,
    val year: String? = null,
    /** One line shown under the carousel while this card is active. */
    val caption: String = "",
    /** Path relative to `resources/public`, e.g. "images/achievements/foo.webp". */
    val imageUrl: String? = null,
    /** Leave empty for a decorative image; the card is already labelled by [title]. */
    val imageAlt: String = "",
    /** Optional link to a certificate or write-up. Rendered only on the active card. */
    val href: String? = null,
    /** Feeds the `<img>` width/height attributes to reserve space and avoid layout shift. */
    val intrinsicWidth: Int? = null,
    val intrinsicHeight: Int? = null,
)

/**
 * Add entries here as felicitation images land in
 * `site/src/jsMain/resources/public/images/achievements/`.
 *
 * Empty is a supported state: the section renders its empty panel and the page
 * height does not change when the first entry is added.
 */
object AchievementsData {
    val items: List<Recognition> = listOf(
        Recognition(
            id = "hackfusion-bog",
            title = "HackFusion — Felicitated by Board of Governance",
            issuer = "HackFusion National Hackathon",
            caption = "Served as Vice President and co-organizer; felicitated by the Board of Governance.",
            imageUrl = "images/achievements/BOG_hackfusion_pic.jpeg",
        ),
        Recognition(
            id = "psb-idea-hackathon",
            title = "PSBs iDEA Hackathon — Winner, ₹1,00,000",
            issuer = "Union Bank of India · PSBs Hackathon Series",
            year = "2025",
            caption = "Led the team to win the Special Category, awarded a ₹1 lakh prize.",
            imageUrl = "images/achievements/PSB_Hackathon_win.jpeg",
        ),
        Recognition(
            id = "ieee-rcsm-paper",
            title = "Published Paper — IEEE RCSM 2025",
            issuer = "IEEE Madhya Pradesh Section · MANIT Bhopal",
            year = "2025",
            caption = "Presented research paper at the 1st International Conference on Recent Trends in Computing and Smart Mobility, MANIT Bhopal.",
            imageUrl = "images/achievements/ResearchPaper1.jpeg",
        ),
        Recognition(
            id = "best-performer-club",
            title = "Best Performer Club of the Year",
            caption = "Won against clubs a decade older, just two years after founding.",
            imageUrl = "images/achievements/WellPerformerClubPic.jpeg",
        ),
    )
}
```

- [ ] **Step 2: Delete the old `utils/Achievement.kt`**

```bash
git rm site/src/jsMain/kotlin/com/vikramaditya/portfolio/utils/Achievement.kt
```

- [ ] **Step 3: Create `components/RecognitionCoverflow.kt`**

This is `components/AchievementCoverflow.kt` with: the composable renamed `AchievementCoverflow` → `RecognitionCoverflow`, every `Achievement` type reference → `Recognition`, two new required parameters `emptyTitle: String` and `emptySubtitle: String` threaded through to `CoverflowEmpty`, and the hardcoded empty-state strings removed.

```kotlin
package com.vikramaditya.portfolio.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.varabyte.kobweb.compose.dom.disposableRef
import com.varabyte.kobweb.compose.dom.registerRefScope
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.vikramaditya.portfolio.styles.*
import com.vikramaditya.portfolio.utils.Recognition
import com.vikramaditya.portfolio.utils.rememberPrefersReducedMotion
import com.vikramaditya.portfolio.utils.theme.Font
import com.vikramaditya.portfolio.utils.theme.Space
import com.vikramaditya.portfolio.utils.theme.Type
import com.vikramaditya.portfolio.utils.theme.colors
import com.vikramaditya.portfolio.utils.theme.fontFace
import com.vikramaditya.portfolio.utils.theme.textStyle
import com.varabyte.kobweb.compose.css.AnimationIterationCount
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

/**
 * A drag-and-flick coverflow of recognition images (achievements, certifications).
 *
 * Branches at the composable level rather than in CSS, so under reduced motion no
 * pointer listeners and no animation loop are ever created in the first place.
 */
@Composable
fun RecognitionCoverflow(
    items: List<Recognition>,
    modifier: Modifier = Modifier,
    label: String = "Achievements",
    emptyTitle: String,
    emptySubtitle: String,
) {
    val reduced = rememberPrefersReducedMotion()
    when {
        items.isEmpty() -> CoverflowEmpty(emptyTitle, emptySubtitle, modifier)
        reduced -> AchievementStrip(items, modifier, label)
        else -> CoverflowPhysics(items, modifier, label)
    }
}

@Composable
private fun CoverflowPhysics(
    items: List<Recognition>,
    modifier: Modifier,
    label: String,
) {
    val activeIndexState = remember(items.size) { mutableStateOf(0) }
    val activeIndex by activeIndexState
    val controller = remember(items.size) { CoverflowController(activeIndexState, items.size) }
    val c = colors(ColorMode.current)
    val lastIndex = items.lastIndex

    Column(modifier = Modifier.fillMaxWidth().then(modifier)) {
        Box(modifier = CoverflowOuterStyle.toModifier()) {
            Div(
                attrs = CoverflowViewportStyle.toModifier().toAttrs {
                    attr("role", "group")
                    attr("aria-roledescription", "carousel")
                    attr("aria-label", label)
                    attr("aria-describedby", "coverflow-help")
                    attr("tabindex", "0")
                }
            ) {
                Div(attrs = CoverflowTrackStyle.toModifier().toAttrs()) {
                    registerRefScope(
                        disposableRef(items.size) { element ->
                            controller.setTrack(element)
                            onDispose { controller.setTrack(null) }
                        }
                    )

                    items.forEachIndexed { index, item ->
                        val isActive = index == activeIndex
                        Div(
                            attrs = CoverflowCardStyle.toModifier().toAttrs {
                                attr("data-cf-index", index.toString())
                                attr("role", "group")
                                attr("aria-roledescription", "slide")
                                attr("aria-label", "${index + 1} of ${items.size}: ${item.title}")
                                if (!isActive) attr("aria-hidden", "true")
                            }
                        ) {
                            registerRefScope(
                                disposableRef(index, items.size) { element ->
                                    controller.setCard(index, element)
                                    onDispose { controller.setCard(index, null) }
                                }
                            )
                            CoverflowCardFace(item)
                        }
                    }
                }

                // Registered last on purpose: Compose dispatches these effects in
                // the order they were recorded, so every card element above is
                // already captured by the time attach() measures and paints.
                registerRefScope(
                    disposableRef(items.size) { element ->
                        controller.attach(element)
                        onDispose { controller.detach() }
                    }
                )
            }
        }

        val active = items.getOrNull(activeIndex)
        Div(attrs = CoverflowCaptionStyle.toModifier().toAttrs()) {
            if (active != null) {
                SpanText(
                    active.title,
                    modifier = Modifier
                        .textStyle(Type.Title)
                        .fontFace(Font.DISPLAY)
                        .color(c.textPrimary)
                )
                val meta = listOfNotNull(active.issuer, active.year).joinToString(" · ")
                if (meta.isNotEmpty() || active.caption.isNotEmpty()) {
                    P(
                        attrs = Modifier
                            .margin(top = Space.sm, bottom = 0.px)
                            .textStyle(Type.Small)
                            .fontFace(Font.BODY)
                            .color(c.textSecondary)
                            .toAttrs()
                    ) {
                        Text(if (active.caption.isNotEmpty()) active.caption else meta)
                    }
                }
            }
        }

        // Controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .margin(top = Space.md)
                .styleModifier { property("gap", "16px") },
            horizontalArrangement = com.varabyte.kobweb.compose.foundation.layout.Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                attrs = CoverflowNavButtonStyle.toModifier().toAttrs {
                    attr("aria-label", "Previous item")
                    attr("type", "button")
                    if (activeIndex <= 0) attr("disabled", "")
                    onClick { controller.step(-1) }
                }
            ) { Text("<") }

            Div(
                attrs = CoverflowDotsStyle.toModifier().toAttrs {
                    attr("role", "group")
                    attr("aria-label", "Choose item")
                }
            ) {
                items.forEachIndexed { index, item ->
                    Button(
                        attrs = CoverflowDotStyle.toModifier().toAttrs {
                            attr("type", "button")
                            attr("aria-label", "Go to ${index + 1} of ${items.size}: ${item.title}")
                            attr("aria-current", if (index == activeIndex) "true" else "false")
                            onClick { controller.goTo(index) }
                        }
                    ) {}
                }
            }

            Button(
                attrs = CoverflowNavButtonStyle.toModifier().toAttrs {
                    attr("aria-label", "Next item")
                    attr("type", "button")
                    if (activeIndex >= lastIndex) attr("disabled", "")
                    onClick { controller.step(1) }
                }
            ) { Text(">") }
        }

        // Announced, not seen. The dots carry this information visually.
        Div(
            attrs = VisuallyHiddenStyle.toModifier().toAttrs {
                attr("aria-live", "polite")
                attr("aria-atomic", "true")
            }
        ) {
            if (active != null) Text("Showing ${activeIndex + 1} of ${items.size}: ${active.title}")
        }
        Div(attrs = VisuallyHiddenStyle.toModifier().toAttrs { attr("id", "coverflow-help") }) {
            Text("Use the left and right arrow keys to move between items.")
        }
    }
}

/** The card's inner face: the image, or a typographic stand-in until one exists. */
@Composable
private fun CoverflowCardFace(item: Recognition) {
    val c = colors(ColorMode.current)
    val imageUrl = item.imageUrl

    if (imageUrl != null) {
        Img(
            src = imageUrl,
            attrs = CoverflowMediaStyle.toModifier().toAttrs {
                attr("alt", item.imageAlt.ifEmpty { item.title })
                attr("loading", "lazy")
                attr("decoding", "async")
                item.intrinsicWidth?.let { attr("width", it.toString()) }
                item.intrinsicHeight?.let { attr("height", it.toString()) }
            }
        )
    } else {
        Div(attrs = CoverflowFallbackStyle.toModifier().toAttrs()) {
            SpanText(
                item.title,
                modifier = Modifier.textStyle(Type.Title).color(c.textPrimary)
            )
            Box(
                modifier = Modifier
                    .margin(topBottom = Space.md)
                    .width(48.px)
                    .height(1.px)
                    .backgroundColor(c.borderStrong)
            )
            val meta = listOfNotNull(item.issuer, item.year).joinToString(" · ")
            if (meta.isNotEmpty()) {
                SpanText(
                    meta,
                    modifier = Modifier
                        .textStyle(Type.Micro)
                        .fontFace(Font.BODY)
                        .color(c.textSecondary)
                )
            }
        }
    }
}

/**
 * Reduced-motion path. A plain scroll-snap strip with no active index at all, so
 * each card carries its own caption and there is nothing to announce.
 */
@Composable
private fun AchievementStrip(
    items: List<Recognition>,
    modifier: Modifier,
    label: String,
) {
    val c = colors(ColorMode.current)

    Div(
        attrs = CoverflowStripStyle.toModifier().then(modifier).toAttrs {
            attr("role", "list")
            attr("aria-label", label)
            attr("tabindex", "0")
        }
    ) {
        items.forEach { item ->
            Div(attrs = Modifier.styleModifier { property("flex", "0 0 auto") }.toAttrs { attr("role", "listitem") }) {
                Div(
                    attrs = CoverflowStripItemStyle.toModifier()
                        .styleModifier { property("aspect-ratio", "16 / 10") }
                        .toAttrs()
                ) {
                    CoverflowCardFace(item)
                }
                Column(modifier = Modifier.margin(top = Space.md).styleModifier {
                    property("max-width", "min(78vw, 460px)")
                }) {
                    SpanText(
                        item.title,
                        modifier = Modifier
                            .textStyle(Type.Body)
                            .fontFace(Font.DISPLAY)
                            .color(c.textPrimary)
                    )
                    val meta = listOfNotNull(item.issuer, item.year).joinToString(" · ")
                    val line = if (item.caption.isNotEmpty()) item.caption else meta
                    if (line.isNotEmpty()) {
                        SpanText(
                            line,
                            modifier = Modifier
                                .margin(top = Space.xs)
                                .textStyle(Type.Micro)
                                .fontFace(Font.BODY)
                                .color(c.textSecondary)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Shown until the first item is added. Sized like a real card so the
 * section does not change height when content arrives.
 */
@Composable
private fun CoverflowEmpty(
    emptyTitle: String,
    emptySubtitle: String,
    modifier: Modifier = Modifier,
) {
    val c = colors(ColorMode.current)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(topBottom = Space.xl)
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Div(attrs = CoverflowEmptyStyle.toModifier().toAttrs()) {
            SpanText(
                emptyTitle,
                modifier = Modifier
                    .textStyle(Type.Title)
                    .fontFace(Font.DISPLAY)
                    .color(c.textPrimary)
            )
            SpanText(
                emptySubtitle,
                modifier = Modifier
                    .margin(top = Space.sm)
                    .textStyle(Type.Small)
                    .fontFace(Font.BODY)
                    .color(c.textSecondary)
            )
            SpanText(
                "_",
                modifier = Modifier
                    .margin(top = Space.sm)
                    .textStyle(Type.Title)
                    .fontFace(Font.DISPLAY)
                    .color(c.signal)
                    .animation(
                        CaretBlink.toAnimation(
                            duration = 1.s,
                            iterationCount = AnimationIterationCount.Infinite,
                        )
                    )
            )
        }
    }
}
```

Note on the nav-button `aria-label`s above: the original hardcoded "Previous achievement" / "Next achievement". Since this component is now shared between Achievements and Certifications, the code block above uses generic `"Previous item"` / `"Next item"` labels instead — that is the actual, final wording to use.

- [ ] **Step 4: Delete the old `components/AchievementCoverflow.kt`**

```bash
git rm site/src/jsMain/kotlin/com/vikramaditya/portfolio/components/AchievementCoverflow.kt
```

- [ ] **Step 5: Update `sections/AchievementsSection.kt` call site**

Replace the full file content:

```kotlin
package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.vikramaditya.portfolio.components.RecognitionCoverflow
import com.vikramaditya.portfolio.utils.AchievementsData
import com.vikramaditya.portfolio.utils.theme.Section
import com.vikramaditya.portfolio.utils.theme.Space

/**
 * Felicitations and recognitions.
 *
 * Content lives in [AchievementsData]; this section renders correctly whether it
 * holds zero, one, or many entries.
 */
@Composable
fun AchievementsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(leftRight = Space.lg, topBottom = Section.gapSm),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RecognitionCoverflow(
            items = AchievementsData.items,
            label = "Achievements",
            emptyTitle = "Felicitations",
            emptySubtitle = "Awards and recognitions are being added here.",
        )
    }
}
```

- [ ] **Step 6: Compile to verify the rename is complete and consistent**

Run: `cd site && ../gradlew :site:compileKotlinJs --rerun-tasks`
Expected: `BUILD SUCCESSFUL`. If it fails, the error will name any remaining `Achievement` / `AchievementCoverflow` reference outside this task's files — grep for it and fix before proceeding.

- [ ] **Step 7: Commit**

```bash
git add -A -- site/src/jsMain/kotlin/com/vikramaditya/portfolio/utils/Recognition.kt \
              site/src/jsMain/kotlin/com/vikramaditya/portfolio/utils/Achievement.kt \
              site/src/jsMain/kotlin/com/vikramaditya/portfolio/components/RecognitionCoverflow.kt \
              site/src/jsMain/kotlin/com/vikramaditya/portfolio/components/AchievementCoverflow.kt \
              site/src/jsMain/kotlin/com/vikramaditya/portfolio/sections/AchievementsSection.kt
git commit -m "Generalize Achievement/AchievementCoverflow into Recognition/RecognitionCoverflow"
```

---

### Task 3: Add certification content

**Files:**
- Create: `site/src/jsMain/kotlin/com/vikramaditya/portfolio/utils/CertificationsData.kt`

**Interfaces:**
- Consumes: `Recognition` data class from `com.vikramaditya.portfolio.utils` (Task 2)
- Produces: `object CertificationsData { val items: List<Recognition> }`

- [ ] **Step 1: Create the file**

```kotlin
package com.vikramaditya.portfolio.utils

/**
 * Top certifications, shown alongside felicitations in their own carousel.
 *
 * Empty is a supported state: the section renders its empty panel and the page
 * height does not change when the first entry is added.
 */
object CertificationsData {
    val items: List<Recognition> = listOf(
        Recognition(
            id = "agentic-ai-engineer",
            title = "Agentic AI Engineer",
            caption = "Agentic AI, MCP, AI Agents",
            imageUrl = "images/certifications/agenticai.jpg",
            intrinsicWidth = 1600,
            intrinsicHeight = 1190,
        ),
        Recognition(
            id = "complete-ai-ml",
            title = "Complete AI/ML",
            caption = "Data Science, Machine Learning, Deep Learning, Natural Language Processing",
            imageUrl = "images/certifications/aiml.jpg",
            intrinsicWidth = 1600,
            intrinsicHeight = 1190,
        ),
        Recognition(
            id = "android-development",
            title = "Android Development",
            caption = "Using Jetpack Compose and Kotlin",
            imageUrl = "images/certifications/android.jpg",
            intrinsicWidth = 1600,
            intrinsicHeight = 1190,
        ),
        Recognition(
            id = "oci-ai-foundations",
            title = "Oracle Certified Professional",
            caption = "AI Foundations Associate",
            imageUrl = "images/certifications/ociaifoundation.jpg",
            intrinsicWidth = 1600,
            intrinsicHeight = 1236,
        ),
        Recognition(
            id = "oci-data-science",
            title = "Oracle Certified Professional",
            caption = "Data Science Professional",
            imageUrl = "images/certifications/ocids.jpg",
            intrinsicWidth = 1600,
            intrinsicHeight = 1236,
        ),
        Recognition(
            id = "oci-generative-ai",
            title = "Oracle Certified Professional",
            caption = "Generative AI Professional",
            imageUrl = "images/certifications/ocigenai.jpg",
            intrinsicWidth = 1600,
            intrinsicHeight = 1236,
        ),
    )
}
```

If Task 1's actual output dimensions differed from 1600x1190 / 1600x1236, use the real values here instead.

- [ ] **Step 2: Compile to verify**

Run: `cd site && ../gradlew :site:compileKotlinJs`
Expected: `BUILD SUCCESSFUL`

- [ ] **Step 3: Commit**

```bash
git add site/src/jsMain/kotlin/com/vikramaditya/portfolio/utils/CertificationsData.kt
git commit -m "Add certification entries"
```

---

### Task 4: Add the Certifications section

**Files:**
- Create: `site/src/jsMain/kotlin/com/vikramaditya/portfolio/sections/CertificationsSection.kt`

**Interfaces:**
- Consumes: `RecognitionCoverflow` (Task 2), `CertificationsData` (Task 3)
- Produces: `@Composable fun CertificationsSection()`

- [ ] **Step 1: Create the file**

```kotlin
package com.vikramaditya.portfolio.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.vikramaditya.portfolio.components.RecognitionCoverflow
import com.vikramaditya.portfolio.utils.CertificationsData
import com.vikramaditya.portfolio.utils.theme.Section
import com.vikramaditya.portfolio.utils.theme.Space

/**
 * Top certifications.
 *
 * Content lives in [CertificationsData]; this section renders correctly whether it
 * holds zero, one, or many entries.
 */
@Composable
fun CertificationsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(leftRight = Space.lg, topBottom = Section.gapSm),
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

- [ ] **Step 2: Compile to verify**

Run: `cd site && ../gradlew :site:compileKotlinJs`
Expected: `BUILD SUCCESSFUL`

- [ ] **Step 3: Commit**

```bash
git add site/src/jsMain/kotlin/com/vikramaditya/portfolio/sections/CertificationsSection.kt
git commit -m "Add CertificationsSection"
```

---

### Task 5: Wire the section into the page

**Files:**
- Modify: `site/src/jsMain/kotlin/com/vikramaditya/portfolio/pages/Index.kt:60`

**Interfaces:**
- Consumes: `CertificationsSection()` (Task 4)

- [ ] **Step 1: Add the new section line**

In `pages/Index.kt`, find:

```kotlin
            RevealedSection("Achievements", id = "achievements", animateBody = false) { AchievementsSection() }
```

Replace with:

```kotlin
            RevealedSection("Achievements", id = "achievements", animateBody = false) { AchievementsSection() }
            RevealedSection("Certifications", id = "certifications", animateBody = false) { CertificationsSection() }
```

(`sections.*` is already a wildcard import in this file, so `CertificationsSection` needs no new import.)

- [ ] **Step 2: Compile to verify**

Run: `cd site && ../gradlew :site:compileKotlinJs`
Expected: `BUILD SUCCESSFUL`

- [ ] **Step 3: Commit**

```bash
git add site/src/jsMain/kotlin/com/vikramaditya/portfolio/pages/Index.kt
git commit -m "Wire CertificationsSection into the home page"
```

---

### Task 6: Full verification

**Files:** none (verification only)

- [ ] **Step 1: Grep for stray references to the old names**

Run: `grep -rn "AchievementCoverflow\|utils\.Achievement\b\|: Achievement\b" site/src/jsMain/kotlin`
Expected: no output (empty). If anything matches, it was missed in Task 2 — fix it and re-run.

- [ ] **Step 2: Start the dev server fresh**

```bash
kobweb stop --notty 2>/dev/null
cd site && (nohup kobweb run --notty > /tmp/kobweb_run.log 2>&1 &)
```

Poll until ready: `until curl -s http://localhost:8080 | grep -q "Certifications"; do sleep 2; done`

- [ ] **Step 3: Manually verify in a real or headless browser**

Open `http://localhost:8080`, scroll to Achievements then Certifications:
- Achievements carousel behaves exactly as before (drag, flick, dots, arrow keys, same "Felicitations" empty-state copy if items were empty — they are not, so verify the 4 existing entries still render).
- Certifications carousel appears directly below Achievements, showing the 6 new entries with their titles and short captions, no issuer/year line.
- Both carousels are independently draggable and don't interfere with each other.

- [ ] **Step 4: Static export must still succeed**

```bash
cd site && ../gradlew :site:kobwebExport 2>&1 | tail -30
```
(or `kobweb export --layout static` if running via the Kobweb CLI directly)

Expected: exits successfully with no errors.

- [ ] **Step 5: Stop the dev server**

```bash
kobweb stop --notty
```

No commit for this task — it's verification only. If any step fails, fix the issue in the relevant earlier task's files, re-run that task's compile check, then resume from Step 1 here.
