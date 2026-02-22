package eu.pw.foodorigin.data.remote.util

import eu.pw.foodorigin.R
import eu.pw.foodorigin.domain.LocalizedException
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.statement.HttpResponse
import timber.log.Timber

suspend inline fun <reified T> safeCall (
	execute: () -> HttpResponse
										): Result<T>{
	val response =
		try {
			execute()
		}catch (rte: HttpRequestTimeoutException){
			Timber.e(rte)
			return Result.failure(LocalizedException(R.string.exception_request_timed_out))
		}catch (e: Exception){
			Timber.e(e)
			return Result.failure(e)
		}
	return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(
	response: HttpResponse
                                               ):Result<T> =
	when(response.status.value){
		in 200..299 ->
			try {
				Result.success(response.body<T>())
			}catch (e: Exception){
				Timber.e(e)
				Result.failure(e)
			}
		408 -> Result.failure(LocalizedException(R.string.exception_request_timed_out))
		404 -> Result.failure(LocalizedException(R.string.exception_product_not_found))
		429 -> Result.failure(LocalizedException(R.string.exception_request_quota_exceeded))
		in 500..599 -> Result.failure(LocalizedException(R.string.exception_something_went_wrong))
		else -> Result.failure(LocalizedException(R.string.exception_unknown_error))
	}


