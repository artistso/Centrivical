# Centrivical

**Centrivical** is a **proposed**, not established, kinematic term for a vector field with inward radial motion and nonzero azimuthal motion about a stated axis and frame:

$$
u_r < 0 \quad\text{and}\quad u_\phi \neq 0.
$$

This repository contains the canonical mini-book manuscript, a formal proposal summary, a buildable Android companion app source tree, and reproducible book-build scripts.

## Contents

| Path | Purpose |
|---|---|
| [`book.md`](book.md) | Canonical manuscript. Edit this file, not generated outputs. |
| [`Proposal.md`](Proposal.md) | Formal proposal summary aligned with the manuscript. |
| [`book/`](book/) | EPUB stylesheet. The cover is generated into `build/book/`. |
| [`scripts/build_book.py`](scripts/build_book.py) | Builds PDF, EPUB, HTML, and Typst intermediates into `build/book/`. |
| [`scripts/package_release.py`](scripts/package_release.py) | Packages book release ZIP and source snapshot into `build/release/`. |
| [`docs/release.md`](docs/release.md) | Exact remaining signing/upload runbook. |
| [`app/`](app/) | Android app: Kotlin + Jetpack Compose. |
| [`docs/publishing.md`](docs/publishing.md) | Google Play Books / Play Store checklist and current-policy notes. |
| [`docs/terminology-search.md`](docs/terminology-search.md) | Prior-use check for “centrivical.” |
| [`docs/verification.md`](docs/verification.md) | What was verified in this checkout and what still requires a local Android toolchain. |
| [`LICENSE`](LICENSE), [`LICENSE-BOOK`](LICENSE-BOOK), [`NOTICE.md`](NOTICE.md) | Licensing split: MIT for code/scripts, CC BY 4.0 for book/proposal text. |

## Book build

The book build uses Pandoc and Typst, not a full LaTeX distribution:

```bash
python3 -m venv .venv
.venv/bin/python -m pip install pypandoc_binary typst pillow pypdf
.venv/bin/python scripts/build_book.py
```

Outputs:

```text
build/book/centrivical.pdf
build/book/centrivical.epub
build/book/centrivical.html
build/book/centrivical.typst
```

Generated outputs are ignored by Git. The EPUB uses MathML; the PDF is compiled from Pandoc’s Typst output with a generated cover page. Preview both formats before publishing, especially equations in EPUB readers.

## Android app

The app is named **Centrivical Explorer**, package `org.centrivical.explorer`. It has three screens:

1. **Define** — proposed status and definition.
2. **Classify** — finite-input-validated interval classifier and signed transverse angle $\beta$.
3. **Explore** — interactive streamline plots for the four canonical examples, with background computation and a sampled centrivicality check.

Build and test with a local Android toolchain:

```bash
./gradlew test
./gradlew assembleDebug
./gradlew assembleRelease   # requires signing configuration
./gradlew bundleRelease     # AAB for Google Play
```

The project targets **API 36** for current Google Play mobile submission requirements. See [`docs/publishing.md`](docs/publishing.md) for the policy sources and release checklist.

## Mathematical scope

The term is a pointwise sign predicate. It does **not** define a force, imply centripetal acceleration, imply incompressibility, imply vorticity, or guarantee that trajectories reach the axis. The boundary-approach example on $r>1$ is included specifically to block that overstatement.

## Prior-use status

A prior-use check is recorded in [`docs/terminology-search.md`](docs/terminology-search.md). It found non-technical uses and apparent misspellings of “centrifugal,” not an established kinematic term. The check is not exhaustive and does not establish priority.

## License

- Android app source, build scripts, and supporting code: **MIT**, see [`LICENSE`](LICENSE).
- Book manuscript and proposal text: **CC BY 4.0**, see [`LICENSE-BOOK`](LICENSE-BOOK).
- Attribution and scope notes: [`NOTICE.md`](NOTICE.md).
