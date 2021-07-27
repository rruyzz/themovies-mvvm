package dominando.android.moviesdb.utils.api

import com.google.firebase.inject.Deferred
import dominando.android.moviesdb.model.DiscoveryListMovieResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


//https://api.themoviedb.org/3/movie/76341?api_key=
//https://api.themoviedb.org/3/movie/550?api_key=e591023d8d396231d3045ea6341a6fd2


interface Service {

    @GET("movie/popular")
    suspend fun getDiscoveryList() : DiscoveryListMovieResponse
}
