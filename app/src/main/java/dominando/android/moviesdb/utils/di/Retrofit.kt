package dominando.android.moviesdb.utils.di

import dominando.android.moviesdb.utils.api.Service
import okhttp3.OkHttpClient
import org.koin.core.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit : KoinComponent {

    fun retrofit() = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp())
        .build()

    fun okHttp() = OkHttpClient.Builder()
        .build()
}

const val API_URL = "https://api.themoviedb.org/"