package dominando.android.moviesdb.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import dominando.android.moviesdb.adapters.MovieSerieItem
import dominando.android.moviesdb.utils.Constanst
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchResponse(
	@SerializedName("page") val page: Int,
	@SerializedName("total_pages") val totalPages: Int,
	@SerializedName("results") val results: List<ResultsItem>,
	@SerializedName("total_results") val totalResults: Int
) : Parcelable

@Parcelize
data class ResultsItem(
	@SerializedName("media_type") val mediaType: String,
	@SerializedName("known_for") val knownFor: List<KnownForItem>,
	@SerializedName("popularity") val popularity: Double,
	@SerializedName("name") val name: String,
	@SerializedName("profile_path") val profilePath: String,
	@SerializedName("id") val id: Int,
	@SerializedName("adult") val adult: Boolean,
	@SerializedName("overview") val overview: String,
	@SerializedName("original_language") val originalLanguage: String,
	@SerializedName("original_title") val originalTitle: String,
	@SerializedName("video") val video: Boolean,
	@SerializedName("title") val titleName: String,
	@SerializedName("genre_ids") val genreIds: List<Int>,
	@SerializedName("poster_path") val posterPath: String,
	@SerializedName("backdrop_path") val backdropPath: String,
	@SerializedName("release_date") val releaseDate: String,
	@SerializedName("vote_average") val voteAverage: Double,
	@SerializedName("vote_count") val voteCount: Int,
	@SerializedName("first_air_date") val firstAirDate: String,
	@SerializedName("origin_country") val originCountry: List<String>,
	@SerializedName("original_name") val originalName: String
) : Parcelable, MovieSerieItem{
	override val title: String
		get() = if (mediaType == "tv") name else titleName
	override val grade: String
		get() = voteAverage.toString()
	override val poster: String
		get() = Constanst.IMAGE_URL + backdropPath
	override val movie_id: Int
		get() = id
	override val isShow: Boolean
		get() =  (mediaType == "tv")
	override val backPoster: String get() = Constanst.IMAGE_URL + backdropPath
}

@Parcelize
data class KnownForItem(
	@SerializedName("overview") val overview: String,
	@SerializedName("original_language") val originalLanguage: String,
	@SerializedName("original_title") val originalTitle: String,
	@SerializedName("video") val video: Boolean,
	@SerializedName("title") val title: String,
	@SerializedName("genre_ids") val genreIds: List<Int>,
	@SerializedName("poster_path") val posterPath: String,
	@SerializedName("backdrop_path") val backdropPath: String,
	@SerializedName("release_date") val releaseDate: String,
	@SerializedName("media_type") val mediaType: String,
	@SerializedName("popularity") val popularity: Double,
	@SerializedName("vote_average") val voteAverage: Double,
	@SerializedName("id") val id: Int,
	@SerializedName("adult") val adult: Boolean,
	@SerializedName("vote_count") val voteCount: Int
) : Parcelable

