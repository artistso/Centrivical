package org.centrivical.explorer

import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.expm1
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.sin

enum class Verdict { CENTRIVICAL, NOT_CENTRIVICAL, UNDETERMINED }

/** Closed, finite uncertainty interval. */
data class Interval(val lo: Double, val hi: Double) {
    init {
        require(lo.isFinite() && hi.isFinite()) { "interval endpoints must be finite" }
        require(lo <= hi) { "lo must be <= hi" }
    }

    fun containsValue(x: Double) = x in lo..hi
}

object Centrivical {
    /** Strict pointwise definition (A4), restricted to finite real components. */
    fun isCentrivical(ur: Double, uphi: Double): Boolean =
        ur.isFinite() && uphi.isFinite() && ur < 0.0 && uphi != 0.0

    /** Measurement classifier (Chapter 9). Intervals must be finite guaranteed enclosures. */
    fun classify(ir: Interval, iphi: Interval): Verdict = when {
        ir.hi < 0.0 && !iphi.containsValue(0.0) -> Verdict.CENTRIVICAL
        ir.lo >= 0.0 || (iphi.lo == 0.0 && iphi.hi == 0.0) -> Verdict.NOT_CENTRIVICAL
        else -> Verdict.UNDETERMINED
    }

    /** Signed transverse angle beta = atan2(u_phi, -u_r), defined only for finite centrivical values. */
    fun beta(ur: Double, uphi: Double): Double? =
        if (isCentrivical(ur, uphi)) atan2(uphi, -ur) else null

    /**
     * Build finite measurement intervals from central values and nonnegative uncertainties.
     * Returns null for NaN/infinite inputs, negative uncertainties, or overflowed endpoints.
     */
    fun measurementIntervals(
        ur: Double,
        dR: Double,
        uphi: Double,
        dPhi: Double
    ): Pair<Interval, Interval>? {
        if (!ur.isFinite() || !dR.isFinite() || !uphi.isFinite() || !dPhi.isFinite()) return null
        if (dR < 0.0 || dPhi < 0.0) return null
        val rLo = ur - dR
        val rHi = ur + dR
        val pLo = uphi - dPhi
        val pHi = uphi + dPhi
        if (!rLo.isFinite() || !rHi.isFinite() || !pLo.isFinite() || !pHi.isFinite()) return null
        return Interval(rLo, rHi) to Interval(pLo, pHi)
    }
}

/** Planar (radial-azimuthal) field. u_z is irrelevant to the predicate (L2). */
data class PlanarField(
    val name: String,
    val formula: String,
    val rMin: Double,                      // inner boundary of D (0 = axis only)
    val ur: (Double, Double) -> Double,    // (r, phi) -> u_r
    val uphi: (Double, Double) -> Double   // (r, phi) -> u_phi
)

val exampleNames = listOf("Linear spiral", "Sink + vortex", "Burgers-type", "Boundary approach")

fun makeField(which: Int, k: Double, s: Double): PlanarField {
    require(which in exampleNames.indices) { "unknown example index" }
    require(k.isFinite() && s.isFinite()) { "field parameters must be finite" }
    return when (which) {
        0 -> PlanarField(
            exampleNames[0], "u_r = −k·r,   u_φ = s·r", 0.0,
            { r, _ -> -k * r }, { r, _ -> s * r }
        )
        1 -> PlanarField(
            exampleNames[1], "u_r = −k/(2πr),   u_φ = s/(2πr)", 0.0,
            { r, _ -> -k / (2 * PI * r) }, { r, _ -> s / (2 * PI * r) }
        )
        2 -> {
            val nu = 0.05 // core fixed with a = 1; exact Burgers vortex when k = 1
            PlanarField(
                exampleNames[2],
                "u_r = −(k/2)·r,   u_φ = s/(2πr)·(1 − e^(−r²/(4ν))),  ν = 0.05\n(exact Burgers vortex when k = 1)",
                0.0,
                { r, _ -> -(k / 2) * r },
                { r, _ -> s / (2 * PI * r) * (-expm1(-r * r / (4 * nu))) }
            )
        }
        else -> PlanarField(
            exampleNames[3], "u_r = −k·(r − 1),   u_φ = s,   on r > 1", 1.0,
            { r, _ -> -k * (r - 1) }, { _, _ -> s }
        )
    }
}

/** Cartesian velocity, or null outside the field domain / for non-finite values. */
fun velocityXY(f: PlanarField, x: Double, y: Double): DoubleArray? {
    if (!x.isFinite() || !y.isFinite()) return null
    val r = hypot(x, y)
    if (r <= f.rMin || r == 0.0) return null
    val phi = atan2(y, x)
    val ur = f.ur(r, phi)
    val up = f.uphi(r, phi)
    if (!ur.isFinite() || !up.isFinite()) return null
    val c = cos(phi)
    val sn = sin(phi)
    return doubleArrayOf(ur * c - up * sn, ur * sn + up * c)
}

/**
 * Streamline traced by RK4 in arc length (direction field u/|u|).
 * Returns an empty list for a seed outside the plotted domain.
 */
fun streamline(
    f: PlanarField,
    x0: Double,
    y0: Double,
    rMax: Double,
    ds: Double = 0.01,
    maxSteps: Int = 3000
): List<Pair<Double, Double>> {
    require(x0.isFinite() && y0.isFinite()) { "seed must be finite" }
    require(rMax.isFinite() && rMax > 0.0) { "rMax must be positive and finite" }
    require(ds.isFinite() && ds > 0.0) { "ds must be positive and finite" }
    require(maxSteps >= 0) { "maxSteps must be nonnegative" }

    val pts = ArrayList<Pair<Double, Double>>()
    var x = x0
    var y = y0
    val r0 = hypot(x, y)
    if (r0 <= f.rMin || r0 > rMax) return pts
    pts.add(x to y)

    val innerGuard = max(f.rMin + 0.02, 0.03)

    fun dir(px: Double, py: Double): DoubleArray? {
        val r = hypot(px, py)
        if (!r.isFinite() || r <= f.rMin || r < innerGuard || r > rMax) return null
        val v = velocityXY(f, px, py) ?: return null
        val n = hypot(v[0], v[1])
        return if (!n.isFinite() || n < 1e-12) null else doubleArrayOf(v[0] / n, v[1] / n)
    }

    repeat(maxSteps) {
        val r = hypot(x, y)
        if (r < innerGuard || r > rMax) return pts
        val k1 = dir(x, y) ?: return pts
        val k2 = dir(x + 0.5 * ds * k1[0], y + 0.5 * ds * k1[1]) ?: return pts
        val k3 = dir(x + 0.5 * ds * k2[0], y + 0.5 * ds * k2[1]) ?: return pts
        val k4 = dir(x + ds * k3[0], y + ds * k3[1]) ?: return pts
        val nx = x + ds / 6 * (k1[0] + 2 * k2[0] + 2 * k3[0] + k4[0])
        val ny = y + ds / 6 * (k1[1] + 2 * k2[1] + 2 * k3[1] + k4[1])
        val nr = hypot(nx, ny)
        if (!nr.isFinite() || nr <= f.rMin || nr < innerGuard || nr > rMax) return pts
        x = nx
        y = ny
        pts.add(x to y)
    }
    return pts
}

/** Sampled check on an annulus. A numerical illustration, not a proof. */
fun centrivicalOnSampledAnnulus(
    f: PlanarField,
    rLo: Double,
    rHi: Double,
    radialSamples: Int = 41,
    angularSamples: Int = 36
): Boolean {
    require(rLo.isFinite() && rHi.isFinite()) { "annulus radii must be finite" }
    require(rLo > f.rMin && rLo > 0.0 && rHi >= rLo) { "annulus must lie inside the field domain" }
    require(radialSamples > 0 && angularSamples > 0) { "sample counts must be positive" }
    return (0 until radialSamples).all { i ->
        val r = rLo + (rHi - rLo) * i / (radialSamples - 1).coerceAtLeast(1)
        (0 until angularSamples).all { j ->
            val phi = 2 * PI * j / angularSamples
            Centrivical.isCentrivical(f.ur(r, phi), f.uphi(r, phi))
        }
    }
}
