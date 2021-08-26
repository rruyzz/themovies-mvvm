package dominando.android.moviesdb.utils.api

import dominando.android.moviesdb.model.DiscoveryMovieResponse
import dominando.android.moviesdb.model.DiscoverySerieResponse
import retrofit2.http.GET
import retrofit2.http.Query


//https://api.themoviedb.org/3/movie/76341?api_key=
//https://api.themoviedb.org/3/movie/550?api_key=e591023d8d396231d3045ea6341a6fd2


interface Service {

    @GET("discover/tv")
    suspend fun getDiscoverySerieList(@Query("api_key") api_key: String) : DiscoverySerieResponse

    @GET("discover/movie")
    suspend fun getDiscoveryMovieList(@Query("api_key") api_key: String) : DiscoveryMovieResponse
}
