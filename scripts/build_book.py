#!/usr/bin/env python3
"""Build the Centrivical mini-book into PDF, EPUB, HTML, and Typst intermediates.

Dependencies:
    python -m pip install pypandoc_binary typst pillow pypdf

Outputs are written to build/book/ and are intentionally not tracked by Git.
"""
from __future__ import annotations

import sys
import zipfile
from pathlib import Path

import pypandoc
import typst
from pypdf import PdfReader

ROOT = Path(__file__).resolve().parents[1]
SOURCE = ROOT / "book.md"
OUT = ROOT / "build" / "book"
COVER = OUT / "cover.jpg"
FONT_DIR = Path("/usr/share/fonts/truetype/dejavu")


def ensure_cover() -> None:
    if not COVER.exists():
        sys.path.insert(0, str(ROOT / "scripts"))
        from render_cover import render_cover

        render_cover(COVER)


def typst_preamble() -> str:
    return """#set document(title: "Centrivical: A Proposed Kinematic Term for Inward Swirling Flow", author: "Centrivical contributors")
#set page(width: 6in, height: 9in, margin: 0.85in, numbering: none)
#set text(font: "DejaVu Serif", size: 11pt, lang: "en")
#set par(justify: true, leading: 0.65em)
#set heading(numbering: "1.")
#show heading: set block(below: 0.8em)
#image("cover.jpg", width: 100%)
#pagebreak()
#set page(width: 6in, height: 9in, margin: 0.85in, numbering: "1")
#outline(title: "Contents", depth: 1)
#pagebreak()
"""


def main() -> None:
    OUT.mkdir(parents=True, exist_ok=True)
    ensure_cover()

    common = ["--top-level-division=chapter"]

    # EPUB 3 with native MathML.
    pypandoc.convert_file(
        str(SOURCE),
        "epub3",
        outputfile=str(OUT / "centrivical.epub"),
        extra_args=common
        + [
            "--mathml",
            f"--epub-cover-image={COVER}",
            f"--css={ROOT / 'book' / 'epub.css'}",
        ],
    )

    # Standalone HTML preview, also MathML.
    pypandoc.convert_file(
        str(SOURCE),
        "html5",
        outputfile=str(OUT / "centrivical.html"),
        extra_args=common + ["--standalone", "--mathml", f"--css={ROOT / 'book' / 'epub.css'}"],
    )

    # Typst body, then wrap with a book-sized preamble and compile to PDF.
    body = pypandoc.convert_file(str(SOURCE), "typst", extra_args=common)
    # Pandoc 3.9 emits a few Typst symbol names that differ from Typst 0.15.
    body = body.replace(" sect ", " ∩ ").replace(" diameter", " ∅")
    typst_source = typst_preamble() + "\n" + body + "\n"
    typst_path = OUT / "centrivical.typst"
    typst_path.write_text(typst_source, encoding="utf-8")
    typst.compile(
        str(typst_path),
        output=str(OUT / "centrivical.pdf"),
        root=str(OUT),
        font_paths=[str(FONT_DIR)] if FONT_DIR.exists() else [],
    )

    # Basic output validation.
    pdf = OUT / "centrivical.pdf"
    epub = OUT / "centrivical.epub"
    html = OUT / "centrivical.html"
    for path in (pdf, epub, html, typst_path):
        if not path.exists() or path.stat().st_size == 0:
            raise SystemExit(f"missing or empty output: {path}")
    pages = len(PdfReader(str(pdf)).pages)
    if pages < 10:
        raise SystemExit(f"PDF has unexpectedly few pages: {pages}")
    with zipfile.ZipFile(epub) as zf:
        names = zf.namelist()
        if not any(name.endswith(".xhtml") for name in names):
            raise SystemExit("EPUB missing XHTML content")
        if not any(name.endswith((".jpg", ".jpeg", ".png")) for name in names) and not any(name.endswith("cover.xhtml") for name in names):
            raise SystemExit("EPUB missing cover image")
    print(f"Built PDF ({pages} pages), EPUB, HTML, and Typst in {OUT}")


if __name__ == "__main__":
    main()
