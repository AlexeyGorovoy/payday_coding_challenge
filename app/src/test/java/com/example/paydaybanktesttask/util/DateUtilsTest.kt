package com.example.paydaybanktesttask.util

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import org.junit.Test
import java.util.*

class DateUtilsTest {

    @Test
    fun `dateFromShortString empty or blank`() {
        assertNull(dateWithoutTimeFromShortString(""))
        assertNull(dateWithoutTimeFromShortString(" "))
    }

    @Test
    fun `dateFromShortString ok`() {
        var expected = createDateWithoutTime(14, 1, 2019)
        var actual = dateWithoutTimeFromShortString("1/14/2019")
        assertEquals(expected, actual)

        expected = createDateWithoutTime(30, 2, 2019)
        actual = dateWithoutTimeFromShortString("2/30/2019")
        assertEquals(expected, actual)

        expected = createDateWithoutTime(31, 12, 2015)
        actual = dateWithoutTimeFromShortString("12/31/2015")
        assertEquals(expected, actual)
    }

    @Test
    fun `dateFromShortString bad`() {
        assertNull(dateWithoutTimeFromShortString("1dd/ss14/2019"))
        assertNull(dateWithoutTimeFromShortString("2/3zz2/2019"))
    }

    @Test
    fun `dateWithoutTimeFromLongString empty or blank`() {
        assertNull(dateWithoutTimeFromLongString(""))
        assertNull(dateWithoutTimeFromLongString(" "))
    }

    @Test
    fun `dateWithoutTimeFromLongString ok`() {
        var expected = createDateWithoutTime(15, 1, 2019)
        var actual = dateWithoutTimeFromLongString("2019-01-15T00:04:05Z")
        assertEquals(expected, actual)

        expected = createDateWithoutTime(30, 2, 2019)
        actual = dateWithoutTimeFromLongString("2019-02-30T00:04:05Z")
        assertEquals(expected, actual)

        expected = createDateWithoutTime(31, 12, 2015)
        actual = dateWithoutTimeFromLongString("2015-12-31T00:04:05Z")
        assertEquals(expected, actual)
    }

    @Test
    fun `dateWithoutTimeFromLongString bad`() {
        assertNull(dateWithoutTimeFromLongString("2019-0s2-30T00:04:05Z"))
        assertNull(dateWithoutTimeFromLongString("2019-02-30sT00:04:05Z"))
    }

    /**
     * Months start from 1
     */
    private fun createDateWithoutTime(day: Int, month: Int, year: Int) = Calendar.getInstance()
        .apply { set(Calendar.DAY_OF_MONTH, day) }
        .apply { set(Calendar.MONTH, month - 1) }
        .apply { set(Calendar.YEAR, year) }
        .time
        .withoutTime()

}