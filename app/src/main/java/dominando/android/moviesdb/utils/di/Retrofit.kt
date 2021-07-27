package dominando.android.moviesdb.utils.di

import dominando.android.moviesdb.utils.Constanst.API_URL
import dominando.android.moviesdb.utils.api.Interceptor
import dominando.android.moviesdb.utils.api.Service
import okhttp3.OkHttpClient
import org.koin.core.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {

//    fun retrofit() = Retrofit.Builder()
//        .baseUrl("https://api.themoviedb.org/")
//        .addConverterFactory(GsonConverterFactory.create())
//        .client(okHttp())
//        .build()
//
//    fun okHttp() = OkHttpClient.Builder()
//        .build()

    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl( API_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    fun provideOkHttpClient(authInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(authInterceptor).build()
    }

    fun provideForecastApi(retrofit: Retrofit) : Service= retrofit.create(Service::class.java)
}
