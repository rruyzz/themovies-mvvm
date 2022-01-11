package dominando.android.moviesdb.utils.api

import dominando.android.moviesdb.model.*
import dominando.android.moviesdb.utils.Constanst.APPEND_TO_RESPONSE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface Service {

    @GET("tv/popular")
    suspend fun getPopularSeries() : Response<SeriesResultsResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies() : Response<MovieResultResponse>

    @GET("movie/upcoming")
    suspend fun getSoonMovies() : Response<MovieResultResponse>

    @GET("movie/upcoming")
    suspend fun getSoonsMovies() : Response<MovieResultResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movie_id: String) : Response<MovieDetailResponse>

    @GET("movie/{movie_id}/watch/providers")
    suspend fun getMoviesProviders(@Path("movie_id") movie_id: String): Response<MovieProviderResponse>

    @GET("tv/{tv_id}/watch/providers")
    suspend fun getSerieProviders(@Path("tv_id") tv_id: String): Response<MovieProviderResponse>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovie(@Path("movie_id") movie_id: String) : Response<MovieResultResponse>

//    @GET("tv/{tv_id}")
//    suspend fun getSerieDetail(@Path("tv_id") tv_id: String) : SerieDetailResponse

    @GET("tv/{tv_id}")
    suspend fun getSerieDetail(@Path("tv_id") tv_id: String,
                               @Query("append_to_response") append_to_response: String= APPEND_TO_RESPONSE) : Response<SerieResponse>

    @GET("tv/{tv_id}/credits")
    suspend fun getCast(@Path("tv_id") tv_id: String) : Response<CastResponse>

    @GET("search/multi")
    suspend fun getSearch(@Query("query") search: String) : Response<SearchResponse>

    @GET("discover/tv")
    suspend fun getDiscoverySeries(@Query("page") page: Int) : Response<SeriesResultsResponse>

    @GET("movie/popular")
    suspend fun getDiscoveryMovies(@Query("page") page: Int) : Response<MovieResultResponse>
}
