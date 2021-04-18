package com.tatsuki.core.extension


import org.junit.Assert.assertEquals
import org.junit.Test

class DateExtensionTest {

    @Test
    fun UnixTimeを日付文字列に変換できること() {
        val dt = 1618751123
        val date = dt.toDate()
        val dateString = date.format("M/dd")
        assertEquals(dateString, "4/18")
    }
}