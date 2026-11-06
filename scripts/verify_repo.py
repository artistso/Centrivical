#!/usr/bin/env python3
"""Lightweight repository checks that do not require an Android SDK."""
from __future__ import annotations

import sys
import xml.etree.ElementTree as ET
import zipfile
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
REQUIRED = [
    "book.md",
    "Proposal.md",
    "README.md",
    "LICENSE",
    "LICENSE-BOOK",
    "NOTICE.md",
    "settings.gradle.kts",
    "build.gradle.kts",
    "gradle.properties",
    "gradlew",
    "gradlew.bat",
    "gradle/wrapper/gradle-wrapper.jar",
    "gradle/wrapper/gradle-wrapper.properties",
    "app/build.gradle.kts",
    "app/src/main/AndroidManifest.xml",
    "app/src/main/java/org/centrivical/explorer/Centrivical.kt",
    "app/src/main/java/org/centrivical/explorer/MainActivity.kt",
    "app/src/test/java/org/centrivical/explorer/CentrivicalTest.kt",
    "app/src/test/java/org/centrivical/explorer/FieldsTest.kt",
    "app/src/test/java/org/centrivical/explorer/StreamlineTest.kt",
    "scripts/build_book.py",
    "scripts/package_release.py",
    "keystore.properties.example",
    "docs/publishing.md",
    "docs/release.md",
    "docs/terminology-search.md",
    "docs/verification.md",
]


def main() -> int:
    failures = []
    for rel in REQUIRED:
        if not (ROOT / rel).exists():
            failures.append(f"missing {rel}")

    for xml in (ROOT / "app/src/main").rglob("*.xml"):
        try:
            ET.parse(xml)
        except ET.ParseError as exc:
            failures.append(f"XML parse error {xml}: {exc}")

    wrapper = ROOT / "gradle/wrapper/gradle-wrapper.jar"
    if wrapper.exists():
        try:
            with zipfile.ZipFile(wrapper) as zf:
                if "org/gradle/wrapper/GradleWrapperMain.class" not in zf.namelist():
                    failures.append("wrapper jar missing GradleWrapperMain")
        except zipfile.BadZipFile:
            failures.append("wrapper jar is not a valid zip")

    for name in ("book.md", "Proposal.md", "README.md"):
        text = (ROOT / name).read_text(encoding="utf-8")
        for placeholder in ("[Author Name]", "[contact]"):
            if placeholder in text:
                failures.append(f"{name} still contains {placeholder}")

    for density in ("mdpi", "hdpi", "xhdpi", "xxhdpi", "xxxhdpi"):
        for icon in ("ic_launcher.png", "ic_launcher_round.png"):
            path = ROOT / "app/src/main/res" / f"mipmap-{density}" / icon
            if not path.exists():
                failures.append(f"missing icon {path}")

    pdf = ROOT / "build/book/centrivical.pdf"
    epub = ROOT / "build/book/centrivical.epub"
    if pdf.exists() and epub.exists():
        try:
            from pypdf import PdfReader

            pages = len(PdfReader(str(pdf)).pages)
            if pages < 10:
                failures.append(f"PDF has too few pages: {pages}")
        except ImportError:
            pass
        with zipfile.ZipFile(epub) as zf:
            if not any(n.endswith(".xhtml") for n in zf.namelist()):
                failures.append("EPUB missing XHTML content")
    else:
        print("note: book outputs not present; run scripts/build_book.py")

    if failures:
        print("FAIL")
        for f in failures:
            print(" -", f)
        return 1
    print("PASS: repository structure, XML, wrapper, icons, and placeholder checks")
    return 0


if __name__ == "__main__":
    sys.exit(main())
