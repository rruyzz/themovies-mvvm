package dominando.android.moviesdb.utils.di

import dominando.android.moviesdb.utils.Constanst.API_URL
import dominando.android.moviesdb.utils.api.Service
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object{

        private val retrofit by lazy {
            val loggin = HttpLoggingInterceptor()
            loggin.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggin)
                .build()
            Retrofit.Builder().baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        val api by lazy {
            retrofit.create(Service::class.java)
        }
    }
}