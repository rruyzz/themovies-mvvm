package dominando.android.moviesdb.utils.api

import dominando.android.moviesdb.model.MovieResultResponse
import dominando.android.moviesdb.model.SeriesResultsResponse
import retrofit2.http.GET
import retrofit2.http.Query


//https://api.themoviedb.org/3/movie/76341?api_key=
//https://api.themoviedb.org/3/movie/550?api_key=e591023d8d396231d3045ea6341a6fd2


interface Service {

    @GET("discover/tv")
    suspend fun getDiscoverySerieList() : SeriesResultsResponse

    @GET("discover/movie")
    suspend fun getDiscoveryMovieList() : MovieResultResponse
}
