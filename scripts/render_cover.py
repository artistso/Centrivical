#!/usr/bin/env python3
"""Generate a simple portrait cover for the Centrivical mini-book."""
from __future__ import annotations

import math
from pathlib import Path

from PIL import Image, ImageDraw, ImageFont

ROOT = Path(__file__).resolve().parents[1]
FONT_DIR = Path("/usr/share/fonts/truetype/dejavu")


def font(name: str, size: int) -> ImageFont.FreeTypeFont:
    return ImageFont.truetype(str(FONT_DIR / name), size)


def render_cover(path: Path) -> None:
    width, height = 1600, 2400
    bg = (13, 27, 42)
    fg = (224, 244, 255)
    accent = (127, 209, 255)
    muted = (158, 188, 209)

    img = Image.new("RGB", (width, height), bg)
    draw = ImageDraw.Draw(img)

    # Inward logarithmic spiral.
    cx, cy = width / 2, 1180
    points = []
    for i in range(1400):
        theta = i / 1399 * 8.5 * math.pi
        r = 560 * math.exp(-0.16 * theta)
        points.append((cx + r * math.cos(theta), cy - r * math.sin(theta)))
    draw.line(points, fill=accent, width=7, joint="curve")
    draw.ellipse((cx - 12, cy - 12, cx + 12, cy + 12), fill=fg)

    # Title block.
    title_font = font("DejaVuSerif-Bold.ttf", 118)
    subtitle_font = font("DejaVuSerif.ttf", 52)
    author_font = font("DejaVuSans.ttf", 42)
    status_font = font("DejaVuSans-Bold.ttf", 36)

    def centered(text: str, y: int, f: ImageFont.FreeTypeFont, fill: tuple[int, int, int]) -> None:
        bbox = draw.textbbox((0, 0), text, font=f)
        w = bbox[2] - bbox[0]
        draw.text(((width - w) / 2, y), text, font=f, fill=fill)

    centered("Centrivical", 210, title_font, fg)
    centered("A Proposed Kinematic Term", 370, subtitle_font, muted)
    centered("for Inward Swirling Flow", 440, subtitle_font, muted)
    centered("Centrivical contributors", 1970, author_font, fg)
    centered("PROPOSED · NOT ESTABLISHED", 2070, status_font, accent)
    centered("u_r < 0   and   u_φ ≠ 0", 2160, font("DejaVuSans.ttf", 44), muted)

    path.parent.mkdir(parents=True, exist_ok=True)
    img.save(path, "JPEG", quality=92, optimize=True)


if __name__ == "__main__":
    render_cover(ROOT / "build" / "book" / "cover.jpg")
