# Publishing plan and checklist

This checklist replaces the time-sensitive publishing notes formerly embedded in `Readme`. Policy items below were checked on **2026-11-05**; recheck them immediately before upload because Google changes requirements.

## Book

| Item | Current choice |
|---|---|
| Title | *Centrivical: A Proposed Kinematic Term for Inward Swirling Flow* |
| Canonical source | `book.md` |
| Author metadata | Centrivical contributors |
| Contact | https://github.com/artistso/Centrivical/issues |
| Year | 2026 |
| Text license | CC BY 4.0 (`LICENSE-BOOK`) |
| Formats | PDF and EPUB 3 |
| ISBN | Optional. Google can assign a GGKEY if no ISBN is supplied. |
| Price | Free is sensible for a proposal. A paid listing requires a payments profile. |
| Citability | Optional Zenodo deposit for a DOI; cite the deposited version in the app description. |

Build:

```bash
.venv/bin/python scripts/build_book.py
```

Then:

1. Open `build/book/centrivical.pdf` and check pagination, cover, and every equation.
2. Open `build/book/centrivical.epub` in an EPUB reader and in Google Play Books Partner Center preview. Check MathML rendering.
3. In Partner Center, add the book, enter metadata, upload both formats, set language/subjects/contributors, choose territories and pricing, and review the preview.
4. Confirm the rights statement matches `LICENSE-BOOK` and `NOTICE.md`.
5. Publish only after the preview is acceptable. Review time varies.

Suggested description:

> A short formal proposal of “centrivical,” a proposed (not established) kinematic term for flows with inward radial drift and nonzero azimuthal velocity. Includes conventions, lemmas, canonical examples, a measurement classifier, and exercises.

Suggested subjects: Science / Physics / Mathematical & Computational; Mathematics / Vector Analysis.

## Android app

| Item | Current choice |
|---|---|
| App name | Centrivical Explorer |
| Package | `org.centrivical.explorer` |
| Min SDK | 24 |
| Compile/target SDK | 36 |
| Direct distribution | Signed APK |
| Google Play | Signed AAB, not APK, for new apps |
| Code license | MIT (`LICENSE`) |
| Data collection | None in the app source |

### Verified Google Play policy notes

- **Target API:** starting August 31, 2026, new apps and app updates for phones/tablets must target Android 16 / API 36 or higher. Source: [Target API level requirements for Google Play apps](https://support.google.com/googleplay/android-developer/answer/11926878?hl=en).
- **Personal-account closed testing:** personal developer accounts created after November 13, 2023 must run a closed test with at least **12 testers opted in continuously for at least 14 days** before applying for production access. Source: [App testing requirements for new personal developer accounts](https://support.google.com/googleplay/android-developer/answer/14151465?hl=en).
- **AAB:** new apps published to Google Play use Android App Bundle format; APKs are for direct distribution/sideload or internal sharing, not new production Play submissions. Source: [Share app bundles and APKs internally](https://support.google.com/googleplay/android-developer/answer/9844679?hl=en).

### Release steps

1. Run `./gradlew test` and `./gradlew assembleDebug` locally.
2. Configure release signing. Back up the keystore and passwords.
3. Build `./gradlew bundleRelease` for Play and, if desired, `./gradlew assembleRelease` for direct APK distribution.
4. Complete Play Console setup: developer account, identity verification, app signing, store listing, screenshots, icon, feature graphic, content rating, target audience, data safety, and privacy policy URL.
5. If the account is a new personal account, complete the closed-test requirement before applying for production access.
6. Recheck target API and testing rules immediately before submission.

Suggested privacy paragraph:

> Centrivical Explorer collects, stores, and transmits no personal or usage data. All computation happens on the device.

Suggested store description:

> Centrivical Explorer accompanies the book Centrivical: A Proposed Kinematic Term for Inward Swirling Flow. “Centrivical” is a proposed, not established, term for flows with inward radial drift and nonzero azimuthal velocity. The app shows the definition, classifies measured velocity components with uncertainty intervals, and plots streamlines of canonical examples. Educational; no data collected.

## Final checklist

- [x] Extract canonical manuscript to `book.md`.
- [x] Align `Proposal.md` with the manuscript’s proposed status and precision.
- [x] Materialize Android project and finite-input validation.
- [x] Add unit tests for classifier, fields, sampling, and streamline behavior.
- [x] Add licensing and attribution notice.
- [x] Record prior-use check.
- [x] Update target API and personal-account testing guidance.
- [x] Build PDF/EPUB in the sandbox.
- [x] Sandbox preflight: rendered all PDF pages, inspected math-heavy pages, and validated EPUB MathML.
- [ ] Final visual inspection in target readers and Google Play Books Partner Center preview.
- [ ] Run Android unit tests and build APK/AAB with a local Android SDK.
- [ ] Create store screenshots and final cover review.
- [ ] Optional: Zenodo DOI and cross-links once both are live.
