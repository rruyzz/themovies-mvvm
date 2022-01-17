package dominando.android.moviesdb.utils

import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.reflect.Type

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
                    else -> {
                        val student = Gson().fromJson(response.body().toString(), ErrorResponse::class.java)
                        ResultWrapper.Error(student)
                    }
                }
            } else ResultWrapper.Error(response as ErrorResponse)
            wrapper

        } catch (throwable: Exception) {
            ResultWrapper.Error(ErrorResponse())
        }
        Log.d("SAFE_REQUEST", resultWrapper.toString())
        return resultWrapper
    }


    data class ErrorResponse(
        @SerializedName("success") val success: Boolean = false,
        @SerializedName("status_code") val status_code: Int = 0,
        @SerializedName("status_message") val status_message: String = ""
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
