package org.centrivical.explorer

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CentrivicalTest {
    @Test fun strict() {
        assertTrue(Centrivical.isCentrivical(-1.0, 0.5))
        assertTrue(Centrivical.isCentrivical(-1.0, -0.5))
        assertFalse(Centrivical.isCentrivical(0.0, 1.0))   // pure rotation
        assertFalse(Centrivical.isCentrivical(-1.0, 0.0))  // pure inflow
        assertFalse(Centrivical.isCentrivical(1.0, 1.0))   // outward spiral
    }

    @Test fun strictRejectsNonFiniteComponents() {
        assertFalse(Centrivical.isCentrivical(Double.NaN, 1.0))
        assertFalse(Centrivical.isCentrivical(-1.0, Double.NaN))
        assertFalse(Centrivical.isCentrivical(Double.NEGATIVE_INFINITY, 1.0))
        assertFalse(Centrivical.isCentrivical(-1.0, Double.POSITIVE_INFINITY))
    }

    @Test(expected = IllegalArgumentException::class)
    fun intervalRejectsNaN() { Interval(Double.NaN, 1.0) }

    @Test(expected = IllegalArgumentException::class)
    fun intervalRejectsInfinity() { Interval(0.0, Double.POSITIVE_INFINITY) }

    @Test(expected = IllegalArgumentException::class)
    fun intervalRejectsReversedEndpoints() { Interval(1.0, 0.0) }

    @Test fun superpositionCounterexample() { // L5
        assertFalse(Centrivical.isCentrivical(-1.0 + -1.0, 1.0 + -1.0))
    }

    @Test fun classifierCertifiedCentrivical() {
        assertEquals(Verdict.CENTRIVICAL, Centrivical.classify(Interval(-1.1, -0.9), Interval(0.4, 0.6)))
        assertEquals(Verdict.CENTRIVICAL, Centrivical.classify(Interval(-1.1, -0.9), Interval(-0.6, -0.4)))
        assertEquals(Verdict.CENTRIVICAL, Centrivical.classify(Interval(-1.0, -1.0), Interval(1.0, 1.0)))
    }

    @Test fun classifierCertifiedNotCentrivical() {
        assertEquals(Verdict.NOT_CENTRIVICAL, Centrivical.classify(Interval(0.0, 0.2), Interval(0.4, 0.6)))
        assertEquals(Verdict.NOT_CENTRIVICAL, Centrivical.classify(Interval(0.0, 0.0), Interval(0.4, 0.6)))
        assertEquals(Verdict.NOT_CENTRIVICAL, Centrivical.classify(Interval(-1.0, -1.0), Interval(0.0, 0.0)))
        assertEquals(Verdict.NOT_CENTRIVICAL, Centrivical.classify(Interval(1.0, 2.0), Interval(0.0, 0.0)))
    }

    @Test fun classifierUndetermined() {
        assertEquals(Verdict.UNDETERMINED, Centrivical.classify(Interval(-0.5, 0.1), Interval(0.9, 1.1))) // Exercise 6
        assertEquals(Verdict.UNDETERMINED, Centrivical.classify(Interval(-1.0, 0.0), Interval(0.9, 1.1)))
        assertEquals(Verdict.UNDETERMINED, Centrivical.classify(Interval(-1.1, -0.9), Interval(-0.1, 0.1)))
        assertEquals(Verdict.UNDETERMINED, Centrivical.classify(Interval(-1.1, -0.9), Interval(0.0, 0.1)))
    }

    @Test fun measurementIntervalsValid() {
        val intervals = Centrivical.measurementIntervals(-1.0, 0.1, 0.5, 0.1)!!
        assertEquals(Interval(-1.1, -0.9), intervals.first)
        assertEquals(Interval(0.4, 0.6), intervals.second)
        assertEquals(Verdict.CENTRIVICAL, Centrivical.classify(intervals.first, intervals.second))
    }

    @Test fun measurementIntervalsRejectInvalidInput() {
        assertNull(Centrivical.measurementIntervals(Double.NaN, 0.1, 0.5, 0.1))
        assertNull(Centrivical.measurementIntervals(-1.0, 0.1, Double.NaN, 0.1))
        assertNull(Centrivical.measurementIntervals(Double.POSITIVE_INFINITY, 0.1, 0.5, 0.1))
        assertNull(Centrivical.measurementIntervals(-1.0, Double.POSITIVE_INFINITY, 0.5, 0.1))
        assertNull(Centrivical.measurementIntervals(-1.0, -0.1, 0.5, 0.1))
        assertNull(Centrivical.measurementIntervals(-1.0, 0.1, 0.5, -0.1))
        assertNull(Centrivical.measurementIntervals(Double.MAX_VALUE, Double.MAX_VALUE, 0.5, 0.1))
        assertNull(Centrivical.measurementIntervals(-Double.MAX_VALUE, Double.MAX_VALUE, 0.5, 0.1))
    }

    @Test fun betaSign() {
        assertTrue(Centrivical.beta(-1.0, 1.0)!! > 0)
        assertTrue(Centrivical.beta(-1.0, -1.0)!! < 0)
        assertNull(Centrivical.beta(1.0, 1.0))
        assertNull(Centrivical.beta(-1.0, 0.0))
        assertNull(Centrivical.beta(Double.NaN, 1.0))
    }
}
