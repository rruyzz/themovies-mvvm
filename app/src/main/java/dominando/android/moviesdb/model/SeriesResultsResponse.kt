package dominando.android.moviesdb.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import dominando.android.moviesdb.adapters.MovieSerieItem
import dominando.android.moviesdb.utils.Constanst
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SeriesResultsResponse(
	@SerializedName("page") val page: Int,
	@SerializedName("total_pages") val totalPages: Int,
	@SerializedName("results") val results: List<SerieItem>,
	@SerializedName("total_results") val totalResults: Int
) : Parcelable

@Parcelize
data class SerieItem(
	@SerializedName("first_air_date") val firstAirDate: String,
	@SerializedName("overview") val overview: String,
	@SerializedName("original_language") val originalLanguage: String,
	@SerializedName("genre_ids") val genreIds: List<Int>,
	@SerializedName("poster_path") val posterPath: String,
	@SerializedName("origin_country") val originCountry: List<String>,
	@SerializedName("backdrop_path") val backdropPath: String,
	@SerializedName("popularity") val popularity: Double,
	@SerializedName("vote_average") val voteAverage: Double,
	@SerializedName("original_name") val originalName: String,
	@SerializedName("name") val name: String,
	@SerializedName("id") val id: Int,
	@SerializedName("vote_count") val voteCount: Int
) : Parcelable, MovieSerieItem {
	override val grade: String get() = voteAverage.toString()
	override val title: String get() = name
	override val poster: String get() = Constanst.IMAGE_URL + posterPath
	override val movie_id: Int get() = id
}