package dominando.android.moviesdb.utils.api

import dominando.android.moviesdb.model.MovieDetailResponse
import dominando.android.moviesdb.model.MovieProviderResponse
import dominando.android.moviesdb.model.MovieResultResponse
import dominando.android.moviesdb.model.SeriesResultsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


//https://api.themoviedb.org/3/movie/76341?api_key=
//https://api.themoviedb.org/3/movie/550?api_key=e591023d8d396231d3045ea6341a6fd2


interface Service {

    @GET("tv/popular")
    suspend fun getPopularSeries() : SeriesResultsResponse

    @GET("movie/popular")
    suspend fun getPopularMovies() : MovieResultResponse

    @GET("movie/upcoming")
    suspend fun getSoonMovies() : MovieResultResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movie_id: String) : MovieDetailResponse

    @GET("movie/{movie_id}/watch/providers")
    suspend fun getMoviesProviders(@Path("movie_id") movie_id: String): MovieProviderResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovie(@Path("movie_id") movie_id: String) : MovieResultResponse
}
