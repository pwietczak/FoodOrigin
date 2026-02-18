package eu.pw.foodorigin.data.mapper

import eu.pw.foodorigin.data.remote.dto.ProductDto
import eu.pw.foodorigin.data.remote.dto.ProductResultDto
import eu.pw.foodorigin.domain.Product
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductResultDtoMapperTest {

    private val mapper = ProductResultDtoMapper()

    @Test
    fun `invoke with null product should map code and nulls for product fields`() {
        val productResultDto = ProductResultDto(
            code = "12345",
            product = null
                                               )
        val expectedProduct = Product(
	        id = null,
	        code = "12345",
	        productName = null,
	        brands = null,
	        brandsTags = null,
	        imageUrl = null,
	        quantity = null,
	        link = null,
                                     )

        val result = mapper(productResultDto)

        assertEquals(expectedProduct, result)
    }

    @Test
    fun `invoke with null product and null code should map null for all fields`() {
        val productResultDto = ProductResultDto(
            code = null,
            product = null
                                               )
        val expectedProduct = Product(
            id = null,
            code = null,
            productName = null,
            brands = null,
            brandsTags = null,
            imageUrl = null,
            quantity = null,
            link = null,
                                     )

        val result = mapper(productResultDto)

        assertEquals(expectedProduct, result)
    }

    @Test
    fun `invoke with full data should map correctly`() {
        val productDto = ProductDto(
            id = "1",
            productName = "Test Product",
            brands = "Test Brand",
            brandsTags = listOf("tag1", "tag2")
        )
        val productResultDto = ProductResultDto(
            code = "12345",
            product = productDto
        )
        val expectedProduct = Product(
            id = "1",
            code = "12345",
            productName = "Test Product",
            brands = "Test Brand",
            brandsTags = listOf("tag1", "tag2"),
            imageUrl = null,
            quantity = null,
            link = null,
        )

        val result = mapper(productResultDto)

        assertEquals(expectedProduct, result)
    }

    @Test
    fun `invoke with partial product data should map correctly`() {
        val productDto = ProductDto(
            id = "1",
            productName = null,
            brands = "Test Brand",
            brandsTags = null
        )
        val productResultDto = ProductResultDto(
            code = "12345",
            product = productDto
        )
        val expectedProduct = Product(
            id = "1",
            code = "12345",
            productName = null,
            brands = "Test Brand",
            brandsTags = null,
            imageUrl = null,
            quantity = null,
            link = null,
        )

        val result = mapper(productResultDto)

        assertEquals(expectedProduct, result)
    }
}
