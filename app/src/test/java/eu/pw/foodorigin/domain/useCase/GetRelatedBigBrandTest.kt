package eu.pw.foodorigin.domain.useCase

import eu.pw.foodorigin.domain.BigBrand
import eu.pw.foodorigin.domain.Product
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class GetRelatedBigBrandTest {

    private val getRelatedBigBrand = GetRelatedBigBrand()

    private val testBigBrand = BigBrand(
        nameUI = "Test Brand",
        keywords = listOf("Test", "Brand"),
        brandsListLink = "www.test.com"
    )

    private val nestleBrand = BigBrand(
        nameUI = "Nestlé",
        keywords = listOf("Nestle", "Winiary"),
        brandsListLink = "www.test.com"
    )

    @Test
    fun `invoke should return null when bigBrands list is empty`() {
        val product = Product(
            id = "1",
            code = "123",
            productName = "Product",
            brands = "Test",
            brandsTags = null,
            imageUrl = null,
            quantity = null,
            link = null
        )
        
        val result = getRelatedBigBrand(product, emptyList())
        
        assertNull(result)
    }

    @Test
    fun `invoke should return BigBrand when product brand matches keyword`() {
        val product = Product(
            id = "1",
            code = "123",
            productName = "Product",
            brands = "Test Brand",
            brandsTags = null,
            imageUrl = null,
            quantity = null,
            link = null
        )
        
        val result = getRelatedBigBrand(product, listOf(testBigBrand))
        
        assertEquals(testBigBrand, result)
    }

    @Test
    fun `invoke should return BigBrand when product brand matches keyword case-insensitively`() {
        val product = Product(
            id = "1",
            code = "123",
            productName = "Product",
            brands = "test",
            brandsTags = null,
            imageUrl = null,
            quantity = null,
            link = null
        )
        
        val result = getRelatedBigBrand(product, listOf(testBigBrand))
        
        assertEquals(testBigBrand, result)
    }

    @Test
    fun `invoke should return BigBrand when product brand tag matches keyword`() {
        val product = Product(
            id = "1",
            code = "123",
            productName = "Product",
            brands = "Other",
            brandsTags = listOf("BrandTag", "Brand"),
            imageUrl = null,
            quantity = null,
            link = null
        )
        
        val result = getRelatedBigBrand(product, listOf(testBigBrand))
        
        assertEquals(testBigBrand, result)
    }

    @Test
    fun `invoke should return null when no match found`() {
        val product = Product(
            id = "1",
            code = "123",
            productName = "Product",
            brands = "Other",
            brandsTags = listOf("None"),
            imageUrl = null,
            quantity = null,
            link = null
        )
        
        val result = getRelatedBigBrand(product, listOf(testBigBrand))
        
        assertNull(result)
    }

    @Test
    fun `invoke should return first matching BigBrand`() {
        val product = Product(
            id = "1",
            code = "123",
            productName = "Product",
            brands = "Winiary",
            brandsTags = null,
            imageUrl = null,
            quantity = null,
            link = null
        )
        val bigBrands = listOf(testBigBrand, nestleBrand)
        
        val result = getRelatedBigBrand(product, bigBrands)
        
        assertEquals(nestleBrand, result)
    }

    @Test
    fun `invoke should return null when BigBrand keyword are empty`() {
        val emptyTermsBrand = BigBrand(
	        nameUI = "Empty",
            brandsListLink = "",
	        keywords = emptyList()
                                      )
        val product = Product(
            id = "1",
            code = "123",
            productName = "Product",
            brands = "Empty",
            brandsTags = null,
            imageUrl = null,
            quantity = null,
            link = null
        )
        
        val result = getRelatedBigBrand(product, listOf(emptyTermsBrand))
        
        assertNull(result)
    }

    @Test
    fun `invoke should return null when product brands and tags are null`() {
        val product = Product(
	        id = "1",
	        code = "123",
	        productName = "Product",
	        brands = null,
	        brandsTags = null,
	        imageUrl = null,
	        quantity = null,
	        link = null
                             )
        
        val result = getRelatedBigBrand(product, listOf(testBigBrand))
        
        assertNull(result)
    }
}
