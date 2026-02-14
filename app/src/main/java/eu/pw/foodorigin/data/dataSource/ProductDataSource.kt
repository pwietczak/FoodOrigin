package eu.pw.foodorigin.data.dataSource

import eu.pw.foodorigin.data.remote.dto.ProductResultDto
import kotlinx.coroutines.flow.Flow

interface ProductDataSource {
	fun getProduct(code: String): Flow<Result<ProductResultDto>>
}