package dominando.android.moviesdb.utils

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

object SafeRequest {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ResultWrapper<Response<T>> {
        val resultWrapper =  try {
            val response = withContext(Dispatchers.IO) {
                apiCall.invoke()
            }
            val wrapper = if (response.body() != null) {
                when (response.code()) {
                    in 200..299 -> {
                        ResultWrapper.Success(response)
                    }
                    else -> ResultWrapper.Error(ErrorResponse(response.message(), response.code()))
                }
            } else ResultWrapper.Error(ErrorResponse(response.message(), response.code()))
            wrapper

        } catch (throwable: Exception) {
            ResultWrapper.Error(ErrorResponse())
        }
        Log.d("SAFE_REQUEST", resultWrapper.toString())
        return resultWrapper
    }


    data class ErrorResponse(
        val message: String = "ERRO DESCONHECIDO",
        val code: Int = 0
    )

}

sealed class ResultWrapper<out T>(val isSuccess: Boolean = false, val successBody: T? = null) {
    data class Success<out T>(val value: T) : ResultWrapper<T>(true, value)
    data class Error(val error: SafeRequest.ErrorResponse? = null) : ResultWrapper<Nothing>(false)
    data class UnknownError(val code: Int? = null, val error: SafeRequest.ErrorResponse? = null) :
        ResultWrapper<Nothing>(false)

    companion object {
        data class ErrorResponse(
            val message: String = "ERRO DESCONHECIDO",
            val code: Int = 0
        )
    }
}
