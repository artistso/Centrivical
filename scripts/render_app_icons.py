#!/usr/bin/env python3
"""Generate legacy PNG launcher icons for API 24/25 devices."""
from __future__ import annotations

import math
from pathlib import Path

from PIL import Image, ImageDraw

ROOT = Path(__file__).resolve().parents[1]
RES = ROOT / "app" / "src" / "main" / "res"
SIZES = {"mdpi": 48, "hdpi": 72, "xhdpi": 96, "xxhdpi": 144, "xxxhdpi": 192}


def icon(size: int, round_icon: bool) -> Image.Image:
    scale = 4
    s = size * scale
    img = Image.new("RGBA", (s, s), (13, 27, 42, 255))
    draw = ImageDraw.Draw(img)
    if round_icon:
        mask = Image.new("L", (s, s), 0)
        ImageDraw.Draw(mask).ellipse((0, 0, s - 1, s - 1), fill=255)
        img.putalpha(mask)
    cx = cy = s / 2
    points = []
    for i in range(600):
        theta = i / 599 * 6.5 * math.pi
        r = (s * 0.36) * math.exp(-0.15 * theta)
        points.append((cx + r * math.cos(theta), cy - r * math.sin(theta)))
    draw.line(points, fill=(127, 209, 255, 255), width=max(2, s // 40), joint="curve")
    r = s * 0.03
    draw.ellipse((cx - r, cy - r, cx + r, cy + r), fill=(255, 255, 255, 255))
    return img.resize((size, size), Image.Resampling.LANCZOS)


def main() -> None:
    for density, size in SIZES.items():
        folder = RES / f"mipmap-{density}"
        folder.mkdir(parents=True, exist_ok=True)
        icon(size, False).save(folder / "ic_launcher.png")
        icon(size, True).save(folder / "ic_launcher_round.png")
    print("Generated launcher PNGs for", ", ".join(SIZES))


if __name__ == "__main__":
    main()
