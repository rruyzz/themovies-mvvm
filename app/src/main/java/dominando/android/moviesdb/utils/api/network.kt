package dominando.android.moviesdb.utils.api

import dominando.android.moviesdb.utils.Constanst
import dominando.android.moviesdb.utils.remoteConfig.RemoteConfig.apiKey
import dominando.android.moviesdb.utils.remoteConfig.RemoteConfig.baseUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.scope.Scope
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


const val CONNECT_TIMEOUT = 15L
const val WRITE_TIMEOUT = 15L
const val READ_TIMEOUT = 15L

var clientInterceptor = Interceptor { chain ->
    var request = chain.request()
    val url = request.url.newBuilder()
        .addQueryParameter("api_key", apiKey)
        .addQueryParameter("language", "pt-BR").build()
    request = request.newBuilder().url(url).build()
    chain.proceed(request)
}

fun retrofitClient(client: OkHttpClient) = Retrofit.Builder()
    .client(client)
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

fun createHttpClient(): OkHttpClient {
    val loggin = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    val client = OkHttpClient.Builder().apply{
        addInterceptor(loggin)
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        addNetworkInterceptor(clientInterceptor)
        readTimeout(5 * 60, TimeUnit.SECONDS)
    }
    return client.build()
}