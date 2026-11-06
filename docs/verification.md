# Verification record

Date: **2026-11-05**.

## Book build

Command:

```bash
.venv/bin/python scripts/build_book.py
```

Result:

- `build/book/centrivical.pdf` — 16 pages, generated cover, contents outline, Typst-compiled math.
- `build/book/centrivical.epub` — EPUB 3 with cover and 168 MathML elements.
- `build/book/centrivical.html` — standalone MathML HTML preview.
- `build/book/centrivical.typst` — intermediate Typst source.

Toolchain used in the sandbox: Pandoc 3.9 via `pypandoc_binary`, Typst 0.15.2 via the `typst` Python package, Pillow, and `pypdf` for output validation. Generated outputs are ignored by Git.

Preflight performed after the build:

- Rendered all 16 PDF pages to PNG and inspected the contact sheet plus math-heavy pages (definition/circulation, trajectories, measurement, transverse angle, exercises/appendix).
- Confirmed EPUB XHTML parses and contains 168 MathML elements.
- Checked that raw TeX appears only inside MathML `<annotation encoding="application/x-tex">` fallback blocks, not as visible non-MathML text.
- Packaged release artifacts with `scripts/package_release.py`.

Still required before publication: open the PDF and EPUB in target readers and Google Play Books Partner Center preview for final human approval.

## Android core tests

Tracked tests:

- `app/src/test/java/org/centrivical/explorer/CentrivicalTest.kt`
- `app/src/test/java/org/centrivical/explorer/FieldsTest.kt`
- `app/src/test/java/org/centrivical/explorer/StreamlineTest.kt`

Sandbox result: **31 tests run, 0 failures**.

The sandbox compiled `Centrivical.kt` and the three test files with Kotlin 2.2.0 and executed them through a temporary JUnit-compatible harness. The real JUnit/Hamcrest jars could not be fetched from the allowed hosts, so this was not a full Gradle `test` run. The tracked tests are standard JUnit 4 tests and should be run locally with:

```bash
./gradlew test
```

Coverage includes:

- strict predicate and non-finite rejection;
- finite interval construction, overflow rejection, and classifier edge cases;
- signed transverse angle;
- all four canonical fields and domain guards;
- sampled annulus positive/negative cases;
- streamline radius/angle behavior, boundary approach, outward motion, zero swirl, and invalid arguments.

## Android project checks

Verified in the sandbox:

- Gradle wrapper JAR is a valid ZIP and contains `GradleWrapperMain`.
- Android manifest and resource XML files parse.
- Launcher PNGs exist for mdpi through xxxhdpi, with adaptive-icon XML for API 26+.
- Project targets compile/target SDK 36 and min SDK 24.

Not verified in the sandbox:

- Full Gradle/AGP build, Compose compilation, APK/AAB generation, and device/emulator run. These require a local Android SDK, Gradle distribution, and Google Maven access.

## Policy and terminology checks

- Google Play target API guidance updated to API 36 for mobile submissions from August 31, 2026.
- Personal-account closed-testing guidance updated to 12 continuously opted-in testers for 14 days.
- AAB requirement for new Play apps retained.
- Prior-use check recorded in `docs/terminology-search.md`; it is lightweight and non-exhaustive.
