package dominando.android.moviesdb.utils.api

import com.google.gson.GsonBuilder
import dominando.android.moviesdb.BuildConfig
import dominando.android.moviesdb.utils.Constanst.API_KEY
import dominando.android.moviesdb.utils.Constanst.API_URL
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.HttpUrl




const val CONNECT_TIMEOUT = 15L
const val WRITE_TIMEOUT = 15L
const val READ_TIMEOUT = 15L
fun Scope.retrofitBuilder(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create(get()))
        .client(get())
        .build()
}

var clientInterceptor = Interceptor { chain ->
    var request = chain.request()
    val url = request.url.newBuilder()
        .addQueryParameter("api_key", API_KEY)
        .addQueryParameter("language", "pt-BR").build()
    request = request.newBuilder().url(url).build()
    chain.proceed(request)
}

fun Scope.retrofitHttpClient(): OkHttpClient {

    return OkHttpClient.Builder().apply {
        cache(get())
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        retryOnConnectionFailure(true)
        addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        addNetworkInterceptor(clientInterceptor)
    }.build()
}