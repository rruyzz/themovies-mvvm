package dominando.android.moviesdb.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SerieDetailResponse(
	@SerializedName("original_language") val originalLanguage: String,
	@SerializedName("number_of_episodes") val numberOfEpisodes: Int,
	@SerializedName("networks") val networks: List<NetworksItem>,
	@SerializedName("type") val type: String,
	@SerializedName("backdrop_path") val backdropPath: String,
	@SerializedName("genres") val genres: List<GenresItem>,
	@SerializedName("popularity") val popularity: Double,
	@SerializedName("production_countries") val productionCountries: List<ProductionCountriesItem>,
	@SerializedName("id") val id: Int,
	@SerializedName("number_of_seasons") val numberOfSeasons: Int,
	@SerializedName("vote_count") val voteCount: Int,
	@SerializedName("first_air_date") val firstAirDate: String,
	@SerializedName("overview") val overview: String,
	@SerializedName("seasons") val seasons: List<SeasonsItem>,
	@SerializedName("languages") val languages: List<String>,
	@SerializedName("created_by") val createdBy: List<CreatedByItem>,
	@SerializedName("last_episode_to_air") val lastEpisodeToAir: LastEpisodeToAir,
	@SerializedName("poster_path") val posterPath: String,
	@SerializedName("origin_country") val originCountry: List<String>,
	@SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguagesItem>,
	@SerializedName("production_companies") val productionCompanies: List<ProductionCompaniesItem>,
	@SerializedName("original_name") val originalName: String,
	@SerializedName("vote_average") val voteAverage: Double,
	@SerializedName("name") val name: String,
	@SerializedName("tagline") val tagline: String,
	@SerializedName("episode_run_time") val episodeRunTime: List<Int>?,
	@SerializedName("in_production") val inProduction: Boolean,
	@SerializedName("last_air_date") val lastAirDate: String,
	@SerializedName("homepage") val homepage: String,
	@SerializedName("status") val status: String
) : Parcelable

@Parcelize
data class CreatedByItem(
	@SerializedName("gender") val gender: Int,
	@SerializedName("credit_id") val creditId: String,
	@SerializedName("name") val name: String,
	@SerializedName("profile_path") val profilePath: String,
	@SerializedName("id") val id: Int
) : Parcelable

@Parcelize
data class NetworksItem(
	@SerializedName("logo_path") val logoPath: String,
	@SerializedName("name") val name: String,
	@SerializedName("id") val id: Int,
	@SerializedName("origin_country") val originCountry: String
) : Parcelable

@Parcelize
data class SeasonsItem(
	@SerializedName("air_date") val airDate: String,
	@SerializedName("overview") val overview: String,
	@SerializedName("episode_count") val episodeCount: Int,
	@SerializedName("name") val name: String,
	@SerializedName("season_number") val seasonNumber: Int,
	@SerializedName("id") val id: Int,
	@SerializedName("poster_path") val posterPath: String ,
	var showEpisodes : Boolean = false,
	var teste : Int = 100
) : Parcelable

@Parcelize
data class LastEpisodeToAir(
	@SerializedName("production_code") val productionCode: String,
	@SerializedName("air_date") val airDate: String,
	@SerializedName("overview") val overview: String,
	@SerializedName("episode_number") val episodeNumber: Int,
	@SerializedName("vote_average") val voteAverage: Double,
	@SerializedName("name") val name: String,
	@SerializedName("season_number") val seasonNumber: Int,
	@SerializedName("id") val id: Int,
	@SerializedName("still_path") val stillPath: String,
	@SerializedName("vote_count") val voteCount: Int
) : Parcelable
