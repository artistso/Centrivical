#!/usr/bin/env python3
"""Package book release artifacts and a source snapshot into build/release/."""
from __future__ import annotations

import hashlib
import json
import subprocess
import zipfile
from datetime import date
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
BOOK_OUT = ROOT / "build" / "book"
RELEASE = ROOT / "build" / "release"
YEAR = date.today().year


def sha256(path: Path) -> str:
    h = hashlib.sha256()
    with path.open("rb") as f:
        for chunk in iter(lambda: f.read(1024 * 1024), b""):
            h.update(chunk)
    return h.hexdigest()


def package_book() -> Path:
    required = [BOOK_OUT / "centrivical.pdf", BOOK_OUT / "centrivical.epub", BOOK_OUT / "cover.jpg"]
    missing = [str(p) for p in required if not p.exists()]
    if missing:
        raise SystemExit("missing book outputs; run scripts/build_book.py first: " + ", ".join(missing))

    RELEASE.mkdir(parents=True, exist_ok=True)
    zip_path = RELEASE / f"centrivical-book-{YEAR}.zip"
    metadata = {
        "title": "Centrivical: A Proposed Kinematic Term for Inward Swirling Flow",
        "author": "Centrivical contributors",
        "year": YEAR,
        "status": "proposed term, not established",
        "text_license": "CC-BY-4.0",
        "contact": "https://github.com/artistso/Centrivical/issues",
        "files": {},
    }
    with zipfile.ZipFile(zip_path, "w", compression=zipfile.ZIP_DEFLATED) as zf:
        for src, arc in [
            (BOOK_OUT / "centrivical.pdf", "centrivical.pdf"),
            (BOOK_OUT / "centrivical.epub", "centrivical.epub"),
            (BOOK_OUT / "cover.jpg", "cover.jpg"),
            (ROOT / "LICENSE-BOOK", "LICENSE-BOOK"),
            (ROOT / "NOTICE.md", "NOTICE.md"),
        ]:
            zf.write(src, arc)
            metadata["files"][arc] = {"sha256": sha256(src), "bytes": src.stat().st_size}
        zf.writestr("metadata.json", json.dumps(metadata, indent=2) + "\n")
        sums = "\n".join(f"{info['sha256']}  {name}" for name, info in metadata["files"].items()) + "\n"
        zf.writestr("SHA256SUMS.txt", sums)
    return zip_path


def package_source() -> Path:
    RELEASE.mkdir(parents=True, exist_ok=True)
    zip_path = RELEASE / f"centrivical-source-{YEAR}.zip"
    files = subprocess.check_output(
        ["git", "ls-files", "--cached", "--others", "--exclude-standard"], cwd=ROOT, text=True
    ).splitlines()
    with zipfile.ZipFile(zip_path, "w", compression=zipfile.ZIP_DEFLATED) as zf:
        for rel in sorted(files):
            path = ROOT / rel
            if path.is_file():
                zf.write(path, rel)
    return zip_path


def main() -> None:
    book = package_book()
    source = package_source()
    print(f"Book release: {book} ({book.stat().st_size} bytes)")
    print(f"Source snapshot: {source} ({source.stat().st_size} bytes)")


if __name__ == "__main__":
    main()
