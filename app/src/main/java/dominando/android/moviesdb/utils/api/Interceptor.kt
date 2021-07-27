package dominando.android.moviesdb.utils.api

import dominando.android.moviesdb.utils.Constanst.API_KEY
import okhttp3.Response
import okhttp3.Interceptor
import retrofit2.http.Query

class Interceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        // DONT INCLUDE API KEYS IN YOUR SOURCE CODE
        val url = req.url().newBuilder().addQueryParameter("key", API_KEY).build()
        req = req.newBuilder().url(url).build()
        return chain.proceed(req)
    }

}
