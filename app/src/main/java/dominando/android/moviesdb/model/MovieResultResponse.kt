package dominando.android.moviesdb.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import dominando.android.moviesdb.adapters.MovieSerieItem
import dominando.android.moviesdb.utils.Constanst
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResultResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val results: List<MovieItem>,
    @SerializedName("total_results") val totalResults: Int
) : Parcelable

@Parcelize
data class MovieItem(
    @SerializedName("overview") val overview: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("video") val video: Boolean,
    @SerializedName("title") val titleMovie: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("id") val id: Int,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("vote_count") val voteCount: Int
) : Parcelable, MovieSerieItem {
    override val grade: String get() =  voteAverage.toString()
    override val poster: String get() = Constanst.IMAGE_URL + posterPath
    override val title: String get() = titleMovie
    override val backPoster: String get() = Constanst.IMAGE_URL + backdropPath
    override val movie_id: Int get() = id
    override val isShow: Boolean get() = false
}