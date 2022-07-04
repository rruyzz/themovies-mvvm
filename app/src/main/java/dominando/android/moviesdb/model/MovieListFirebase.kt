package dominando.android.moviesdb.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class MovieListFirebase(
    val movies: Array<MovieFirebase> = arrayOf()
)
@Parcelize
data class MovieFirebase(
    @SerializedName("movie_id") val movie_id: String = "",
    @SerializedName("hasWatched") val hasWatched: Boolean = false,
    @SerializedName("name") val name: String = "",
    @SerializedName("poster") val poster: String = ""
) : Parcelable {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "movie_id" to movie_id,
            "hasWatched" to hasWatched,
            "name" to name,
            "poster" to poster,
        )
    }
}

@Parcelize
data class User(
    @SerializedName("name")val name: String = "",
    @SerializedName("id")val id: String = "",
    @SerializedName("movies")val movies: List<MovieFirebase?>? = listOf(),
    @SerializedName("series")val series: List<MovieFirebase?>? = listOf()
) : Parcelable
@Parcelize

data class Users(
    @SerializedName("users")val users: List<User?>? = listOf()
) : Parcelable