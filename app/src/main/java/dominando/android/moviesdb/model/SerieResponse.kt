package dominando.android.moviesdb.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SerieResponse(
	@SerializedName("original_language") val originalLanguage: String,
	@SerializedName("number_of_episodes") val numberOfEpisodes: Int,
	@SerializedName("networks") val networks: List<NetworksItem>,
	@SerializedName("type") val type: String,
	@SerializedName("season/1") val season1: Season? = null,
	@SerializedName("season/2") val season2: Season? = null,
	@SerializedName("season/3") val season3: Season? = null,
	@SerializedName("season/4") val season4: Season? = null,
	@SerializedName("season/5") val season5: Season? = null,
	@SerializedName("season/6") val season6: Season? = null,
	@SerializedName("season/7") val season7: Season? = null,
	@SerializedName("season/8") val season8: Season? = null,
	@SerializedName("season/9") val season9: Season? = null,
	@SerializedName("season/10") val season10: Season? = null,
	@SerializedName("season/11") val season11: Season? = null,
	@SerializedName("season/12") val season12: Season? = null,
	@SerializedName("season/13") val season13: Season? = null,
	@SerializedName("season/14") val season14: Season? = null,
	@SerializedName("season/15") val season15: Season? = null,
	@SerializedName("season/16") val season16: Season? = null,
	@SerializedName("season/17") val season17: Season? = null,
	@SerializedName("season/18") val season18: Season? = null,
	@SerializedName("season/19") val season19: Season? = null,
	@SerializedName("season/20") val season20: Season? = null,
	@SerializedName("backdrop_path") val backdropPath: String,
	@SerializedName("genres") val genres: List<GenresItem?>?,
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
	@SerializedName("episode_run_time") val episodeRunTime: List<Int>,
	@SerializedName("next_episode_to_air") val nextEpisodeToAir: NextEpisodeToAir,
	@SerializedName("in_production") val inProduction: Boolean,
	@SerializedName("last_air_date") val lastAirDate: String,
	@SerializedName("homepage") val homepage: String,
	@SerializedName("status") val status: String,
) : Parcelable{
	val seasonList get() = listOf(season1, season2, season3, season4, season5,
			season6, season7, season8, season9, season10, season11, season12, season13, season14,
			season15, season16, season17, season18, season19, season20)

}

@Parcelize
data class Season(
	@SerializedName("air_date") val airDate: String,
	@SerializedName("overview") val overview: String,
	@SerializedName("name") val name: String,
	@SerializedName("season_number") val seasonNumber: Int,
	@SerializedName("_id") val id: String,
	@SerializedName("episodes") val episodes: List<EpisodesItem>,
	@SerializedName("poster_path") val posterPath: String,
	var showEpisodes : Boolean = false
) : Parcelable

@Parcelize
data class EpisodesItem(
	@SerializedName("production_code") val productionCode: String,
	@SerializedName("air_date") val airDate: String,
	@SerializedName("overview") val overview: String,
	@SerializedName("episode_number") val episodeNumber: Int,
	@SerializedName("vote_average") val voteAverage: Double,
	@SerializedName("name") val name: String,
	@SerializedName("season_number") val seasonNumber: Int,
	@SerializedName("id") val id: Int,
	@SerializedName("still_path") val stillPath: String,
	@SerializedName("vote_count") val voteCount: Int,
	@SerializedName("crew") val crew: List<CrewItem>,
	@SerializedName("guest_stars") val guestStars: List<ActorsItem>,
	var isVisible: Boolean = false
) : Parcelable

@Parcelize
data class NextEpisodeToAir(
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


@Parcelize
data class GuestStarsItem(
	@SerializedName("character") val character: String,
	@SerializedName("gender") val gender: Int,
	@SerializedName("credit_id") val creditId: String,
	@SerializedName("known_for_department") val knownForDepartment: String,
	@SerializedName("original_name") val originalName: String,
	@SerializedName("popularity") val popularity: Double,
	@SerializedName("name") val name: String,
	@SerializedName("profile_path") val profilePath: String,
	@SerializedName("id") val id: Int,
	@SerializedName("adult") val adult: Boolean,
	@SerializedName("order") val order: Int
) : Parcelable
