package dominando.android.moviesdb.utils.api

import dominando.android.moviesdb.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

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

    @GET("tv/{tv_id}/watch/providers")
    suspend fun getSerieProviders(@Path("tv_id") tv_id: String): MovieProviderResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovie(@Path("movie_id") movie_id: String) : MovieResultResponse

    @GET("tv/{tv_id}")
    suspend fun getSerieDetail(@Path("tv_id") tv_id: String) : SerieDetailResponse

    @GET("tv/{tv_id}/credits")
    suspend fun getCast(@Path("tv_id") tv_id: String) : CastResponse

    @GET("search/multi")
    suspend fun getSearch(@Query("query") search: String) : SearchResponse

    @GET("discover/tv")
    suspend fun getDiscoverySeries(@Query("page") page: Int) : SeriesResultsResponse

    @GET("movie/popular")
    suspend fun getDiescoveryMovies(@Query("page") page: Int) : MovieResultResponse
}
