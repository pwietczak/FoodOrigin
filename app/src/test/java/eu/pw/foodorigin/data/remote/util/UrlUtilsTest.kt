package eu.pw.foodorigin.data.remote.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class UrlUtilsTest {

    @Test
    fun `formatUrl returns null when input is null`() {
        assertNull(formatUrl(null))
    }

    @Test
    fun `formatUrl returns null when input is blank`() {
        assertNull(formatUrl(""))
        assertNull(formatUrl("   "))
    }

    @Test
    fun `formatUrl prepends https when protocol is missing`() {
        assertEquals("https://google.com", formatUrl("google.com"))
        assertEquals("https://www.google.com", formatUrl("www.google.com"))
    }

    @Test
    fun `formatUrl keeps original url when it starts with http`() {
        assertEquals("http://example.com", formatUrl("http://example.com"))
    }

    @Test
    fun `formatUrl keeps original url when it starts with https`() {
        assertEquals("https://example.com", formatUrl("https://example.com"))
    }

    @Test
    fun `formatUrl handles case insensitivity for protocols`() {
        assertEquals("HTTP://example.com", formatUrl("HTTP://example.com"))
        assertEquals("HTTPS://example.com", formatUrl("HTTPS://example.com"))
    }

    @Test
    fun `formatUrl trims whitespace`() {
        assertEquals("https://example.com", formatUrl("  example.com  "))
        assertEquals("https://example.com", formatUrl("\nexample.com\t"))
    }
}
