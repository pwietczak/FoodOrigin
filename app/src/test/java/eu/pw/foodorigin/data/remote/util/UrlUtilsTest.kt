package eu.pw.foodorigin.data.remote.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class UrlUtilsTest {

    @Test
    fun `formatUrl returns null when input is null`() {
        assertNull(normalizeUrl(null))
    }

    @Test
    fun `formatUrl returns null when input is blank`() {
        assertNull(normalizeUrl(""))
        assertNull(normalizeUrl("   "))
    }

    @Test
    fun `formatUrl prepends https when protocol is missing`() {
        assertEquals("https://google.com", normalizeUrl("google.com"))
        assertEquals("https://www.google.com", normalizeUrl("www.google.com"))
    }

    @Test
    fun `formatUrl keeps original url when it starts with http`() {
        assertEquals("http://example.com", normalizeUrl("http://example.com"))
    }

    @Test
    fun `formatUrl keeps original url when it starts with https`() {
        assertEquals("https://example.com", normalizeUrl("https://example.com"))
    }

    @Test
    fun `formatUrl handles case insensitivity for protocols`() {
        assertEquals("HTTP://example.com", normalizeUrl("HTTP://example.com"))
        assertEquals("HTTPS://example.com", normalizeUrl("HTTPS://example.com"))
    }

    @Test
    fun `formatUrl trims whitespace`() {
        assertEquals("https://example.com", normalizeUrl("  example.com  "))
        assertEquals("https://example.com", normalizeUrl("\nexample.com\t"))
    }
}
