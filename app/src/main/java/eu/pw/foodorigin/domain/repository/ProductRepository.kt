package eu.pw.foodorigin.domain.repository

import eu.pw.foodorigin.domain.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
	fun getProduct(code: String): Flow<Result<Product>>
}