# Release runbook

This is the exact remaining path from the current checkout to publication. The sandbox cannot log into Google accounts, hold signing passwords, or run a full Android SDK build.

## 1. Book release

Artifacts already built:

```text
build/book/centrivical.pdf
build/book/centrivical.epub
build/release/centrivical-book-2026.zip
```

Rebuild and package:

```bash
.venv/bin/python scripts/build_book.py
.venv/bin/python scripts/package_release.py
```

Upload steps:

1. Open Google Play Books Partner Center: https://play.google.com/books/publish.
2. Add book. Use title/subtitle from `docs/publishing.md`; author is currently `Centrivical contributors`.
3. Upload `centrivical.pdf` and `centrivical.epub`.
4. Set language English, subjects Science / Physics / Mathematical & Computational and Mathematics / Vector Analysis, free pricing if desired, and worldwide territories if you hold rights.
5. Use the CC BY 4.0 rights statement from `LICENSE-BOOK` / `NOTICE.md`.
6. Inspect the Partner Center preview, especially EPUB equations. Publish only after the preview is acceptable.
7. Optional: deposit the PDF on Zenodo for a DOI, then add the DOI to the app description and repository README.

## 2. Android release

Requirements: JDK 17, Android SDK 36, Android Studio or command-line tools, and a Play Console developer account if publishing to Google Play.

Run tests and debug build:

```bash
./gradlew test
./gradlew assembleDebug
```

Create a release keystore once. Back it up and never commit it:

```bash
keytool -genkeypair -v \
  -keystore centrivical-release.jks \
  -alias centrivical \
  -keyalg RSA -keysize 2048 -validity 10000
cp keystore.properties.example keystore.properties
# Edit keystore.properties with the real passwords.
```

Build signed release artifacts:

```bash
./gradlew bundleRelease     # Google Play AAB
./gradlew assembleRelease   # direct-distribution APK
```

Expected outputs:

```text
app/build/outputs/bundle/release/app-release.aab
app/build/outputs/apk/release/app-release.apk
```

If `keystore.properties` is absent, release builds are unsigned and are not suitable for Play upload.

Play Console steps:

1. Create app, choose app/game, free/paid, and declarations.
2. Enable Play App Signing.
3. Upload the AAB to internal testing first, then closed testing if required.
4. Complete store listing, screenshots, icon, feature graphic, content rating, target audience, data safety, and privacy policy URL.
5. For new personal accounts, satisfy the closed-test requirement before applying for production access.
6. Recheck target API and testing rules immediately before submission.

## 3. Final human checks

- [ ] PDF/EPUB preview in target readers and Partner Center.
- [ ] `./gradlew test` passes locally.
- [ ] Debug APK installs and the three screens work on a device/emulator.
- [ ] Signed AAB uploaded to internal testing.
- [ ] Store listing and policy forms complete.
- [ ] Keystore backed up offline.
