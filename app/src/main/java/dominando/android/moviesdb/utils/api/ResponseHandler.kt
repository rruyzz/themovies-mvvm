package dominando.android.moviesdb.utils.api

import okhttp3.internal.http2.ErrorCode
import retrofit2.HttpException
import java.net.SocketTimeoutException

open class ResponseHandler {
    fun <T : Any> handleSuccess(data: T): Resources<T> {
        return Resources.success(data)
    }

    fun <T : Any> handleException(e: Exception): Resources<T> {
        return when (e) {
            is HttpException -> Resources.error(getErrorMessage(e.code()), null)
            is SocketTimeoutException -> Resources.error(
                getErrorMessage(ErrorCode.INTERNAL_ERROR.httpCode),
                null
            )
            else -> Resources.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            ErrorCode.INTERNAL_ERROR.httpCode -> "Timeout"
            401 -> "Unauthorised"
            404 -> "Not found"
            else -> "Something went wrong"
        }
    }
}
