package com.tatsuki.core.extension


import com.tatsuki.data.extension.format
import com.tatsuki.data.extension.toDate
import org.junit.Assert.assertEquals
import org.junit.Test

class DateExtensionTest {

    @Test
    fun UnixTimeを日付文字列に変換できること() {
        val dt = 1618751123
        val date = dt.toDate()
        val dateString = date.format("M/dd")
        val dayOfWeek = date.format("E曜日")
        assertEquals(dateString, "4/18")
        assertEquals(dayOfWeek, "日曜日")
    }
}