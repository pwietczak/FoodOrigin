package eu.pw.foodorigin.data

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import timber.log.Timber

suspend inline fun <reified T> safeCall (
	execute: () -> HttpResponse
										): Result<T>{
	val response =
		try {
			execute()
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
		408 -> Result.failure(Exception("The request timed out"))
		429 -> Result.failure(Exception("Request quota exceeded"))
		in 500..599 -> Result.failure(Exception("Something went wrong"))
		else -> Result.failure(Exception("Unknown error"))
	}
