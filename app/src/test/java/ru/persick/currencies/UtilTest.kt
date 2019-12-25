package ru.persick.currencies

import org.junit.Assert.*
import org.junit.Test
import ru.persick.currencies.Util.decimalFormat
import ru.persick.currencies.Util.decimalFormatToDouble
import ru.persick.currencies.Util.getCurrencyName

class UtilTest {

    @Test
    fun nameTest() {
        val name = getCurrencyName("USD")
        assertTrue(name == "US Dollar")
    }

    @Test
    fun nameTestFail() {
        val name = getCurrencyName("QQQ")
        assertTrue(name == "")
    }

    @Test
    fun decimalFormat1() {
        val decimalFormat = 13.3.decimalFormat()
        assertTrue(decimalFormat == "13.3")
    }

    @Test
    fun decimalFormat2() {
        val decimalFormat = 13.345.decimalFormat()
        assertTrue(decimalFormat == "13.35")
    }

    @Test
    fun decimalFormat3() {
        val decimalFormat = 13.0.decimalFormat()
        assertTrue(decimalFormat == "13")
    }

    @Test
    fun decimalFormat4() {
        val decimalFormat = 0.0.decimalFormat()
        assertTrue(decimalFormat == "")
    }

    @Test
    fun decimalFormat5() {
        val decimalFormat = 1.001.decimalFormat()
        assertTrue(decimalFormat == "1")
    }

    @Test
    fun decimalFormatToDouble1() {
        val double = "123".decimalFormatToDouble()
        assertTrue(double == 123.0)
    }

    @Test
    fun decimalFormatToDouble2() {
        val double = "123.5".decimalFormatToDouble()
        assertTrue(double == 123.5)
    }

    @Test
    fun decimalFormatToDouble3() {
        val double = "test".decimalFormatToDouble()
        assertTrue(double == 0.0)
    }

    @Test
    fun decimalFormatToDouble4() {
        val double = "0".decimalFormatToDouble()
        assertTrue(double == 0.0)
    }

    @Test
    fun decimalFormatToDouble5() {
        val double = "".decimalFormatToDouble()
        assertTrue(double == 0.0)
    }
}