package org.centrivical.explorer

import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.math.PI
import kotlin.math.exp

class FieldsTest {
    private val eps = 1e-12

    @Test(expected = IllegalArgumentException::class)
    fun makeFieldRejectsUnknownIndex() { makeField(99, 1.0, 1.0) }

    @Test(expected = IllegalArgumentException::class)
    fun makeFieldRejectsNonFiniteParameters() { makeField(0, Double.NaN, 1.0) }

    @Test fun linearSpiralComponents() {
        val f = makeField(0, 2.0, 3.0)
        assertEquals(-4.0, f.ur(2.0, 0.0), eps)
        assertEquals(6.0, f.uphi(2.0, 0.0), eps)
        assertArrayEquals(doubleArrayOf(-4.0, 6.0), velocityXY(f, 2.0, 0.0)!!, eps)
        assertNull(velocityXY(f, 0.0, 0.0))
    }

    @Test fun sinkVortexComponents() {
        val f = makeField(1, 2 * PI, 4 * PI)
        assertEquals(-1.0, f.ur(1.0, 0.0), eps)
        assertEquals(2.0, f.uphi(1.0, 0.0), eps)
        assertNull(velocityXY(f, 0.0, 0.0))
    }

    @Test fun burgersTypeUsesStableExpm1() {
        val f = makeField(2, 1.0, 2 * PI)
        val r = 1.0
        val expected = (1 - exp(-r * r / (4 * 0.05)))
        assertEquals(expected, f.uphi(r, 0.0), 1e-14)
        assertEquals(-0.5, f.ur(r, 0.0), eps)
    }

    @Test fun boundaryApproachDomain() {
        val f = makeField(3, 1.0, 1.0)
        assertEquals(1.0, f.rMin, eps)
        assertNull(velocityXY(f, 1.0, 0.0))
        assertArrayEquals(doubleArrayOf(-1.0, 1.0), velocityXY(f, 2.0, 0.0)!!, eps)
    }

    @Test fun sampledAnnulusPositiveAndNegativeCases() {
        assertTrue(centrivicalOnSampledAnnulus(makeField(0, 1.0, 1.0), 0.05, 2.0))
        assertFalse(centrivicalOnSampledAnnulus(makeField(0, 1.0, 0.0), 0.05, 2.0))
        assertFalse(centrivicalOnSampledAnnulus(makeField(0, 0.0, 1.0), 0.05, 2.0))
        assertFalse(centrivicalOnSampledAnnulus(makeField(0, -1.0, 1.0), 0.05, 2.0))
        assertTrue(centrivicalOnSampledAnnulus(makeField(3, 1.0, 1.0), 1.05, 2.0))
        assertFalse(centrivicalOnSampledAnnulus(makeField(3, 1.0, 0.0), 1.05, 2.0))
    }

    @Test(expected = IllegalArgumentException::class)
    fun sampledAnnulusRejectsAxis() { centrivicalOnSampledAnnulus(makeField(0, 1.0, 1.0), 0.0, 2.0) }

    @Test(expected = IllegalArgumentException::class)
    fun sampledAnnulusRejectsBoundaryDomainViolation() { centrivicalOnSampledAnnulus(makeField(3, 1.0, 1.0), 1.0, 2.0) }

    @Test(expected = IllegalArgumentException::class)
    fun sampledAnnulusRejectsBadSampleCount() { centrivicalOnSampledAnnulus(makeField(0, 1.0, 1.0), 0.05, 2.0, radialSamples = 0) }
}
