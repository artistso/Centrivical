package org.centrivical.explorer

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.hypot

class StreamlineTest {
    @Test(expected = IllegalArgumentException::class)
    fun rejectsZeroStep() { streamline(makeField(0, 1.0, 1.0), 1.0, 0.0, 2.0, ds = 0.0) }

    @Test(expected = IllegalArgumentException::class)
    fun rejectsNonFiniteSeed() { streamline(makeField(0, 1.0, 1.0), Double.NaN, 0.0, 2.0) }

    @Test(expected = IllegalArgumentException::class)
    fun rejectsNegativeMaxSteps() { streamline(makeField(0, 1.0, 1.0), 1.0, 0.0, 2.0, maxSteps = -1) }

    @Test fun seedOutsideDomainReturnsEmpty() {
        assertTrue(streamline(makeField(3, 1.0, 1.0), 1.0, 0.0, 2.0).isEmpty())
        assertTrue(streamline(makeField(0, 1.0, 1.0), 3.0, 0.0, 2.0).isEmpty())
    }

    @Test fun zeroStepsReturnsSeedOnly() {
        val pts = streamline(makeField(0, 1.0, 1.0), 1.0, 0.0, 2.0, maxSteps = 0)
        assertEquals(1, pts.size)
        assertEquals(1.0 to 0.0, pts[0])
    }

    @Test fun linearInwardSpiralDecreasesRadiusAndIncreasesAngle() {
        val pts = streamline(makeField(0, 1.0, 1.0), 1.5, 0.0, 2.0, ds = 0.01, maxSteps = 500)
        assertTrue(pts.size > 2)
        val radii = pts.map { hypot(it.first, it.second) }
        assertTrue(radii.zipWithNext().all { (a, b) -> b < a })
        assertTrue(radii.last() < radii.first())
        assertTrue(radii.all { it.isFinite() && it >= 0.03 })

        var prev = atan2(pts[0].second, pts[0].first)
        var unwrapped = prev
        for (p in pts.drop(1)) {
            val a = atan2(p.second, p.first)
            var d = a - prev
            if (d < -PI) d += 2 * PI
            if (d > PI) d -= 2 * PI
            unwrapped += d
            prev = a
        }
        assertTrue(unwrapped > 0)
    }

    @Test fun boundaryApproachStaysOutsideInnerBoundary() {
        val pts = streamline(makeField(3, 1.0, 1.0), 1.8, 0.0, 2.0, ds = 0.01, maxSteps = 1000)
        assertTrue(pts.size > 2)
        val radii = pts.map { hypot(it.first, it.second) }
        assertTrue(radii.all { it > 1.0 && it.isFinite() })
        assertTrue(radii.zipWithNext().all { (a, b) -> b <= a })
        assertTrue(radii.last() < 1.2)
    }

    @Test fun outwardFieldIncreasesRadiusUntilPlotBoundary() {
        val pts = streamline(makeField(0, -1.0, 1.0), 1.0, 0.0, 2.0, ds = 0.01, maxSteps = 500)
        assertTrue(pts.size > 1)
        val radii = pts.map { hypot(it.first, it.second) }
        assertTrue(radii.zipWithNext().all { (a, b) -> b >= a })
        assertTrue(radii.last() <= 2.0)
    }

    @Test fun zeroSwirlProducesRadialLine() {
        val pts = streamline(makeField(0, 1.0, 0.0), 1.0, 0.0, 2.0, ds = 0.01, maxSteps = 200)
        assertTrue(pts.size > 1)
        assertTrue(pts.all { kotlin.math.abs(it.second) < 1e-9 })
        assertTrue(pts.map { it.first }.zipWithNext().all { (a, b) -> b < a })
    }
}
