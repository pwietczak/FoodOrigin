package eu.pw.foodorigin.data.remote

import eu.pw.foodorigin.data.constructUrl
import eu.pw.foodorigin.data.dataSource.ProductDataSource
import eu.pw.foodorigin.data.remote.dto.ProductResultDto
import eu.pw.foodorigin.data.safeCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteProductDataSource(
	private val httpClient: HttpClient,
                             ) : ProductDataSource {
	override fun getProduct(code: String): Flow<Result<ProductResultDto>> =
		flow {
			val result = safeCall<ProductResultDto> {
				httpClient.get(constructUrl("/product/$code"))
			}
			emit(result)
		}.flowOn(Dispatchers.IO)

}