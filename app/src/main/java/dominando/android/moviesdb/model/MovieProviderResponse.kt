package dominando.android.moviesdb.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieProviderResponse(
	@SerializedName("id") val id: Int,
	@SerializedName("results") val results: Results
) : Parcelable

@Parcelize
data class FlatrateItem(
	@SerializedName("logo_path") val logoPath: String,
	@SerializedName("provider_id") val providerId: Int,
	@SerializedName("display_priority") val displayPriority: Int,
	@SerializedName("provider_name") val providerName: String
) : Parcelable

@Parcelize
data class Results(
	@SerializedName("BR") val bR: BR?
) : Parcelable

@Parcelize
data class BuyItem(
	@SerializedName("logo_path") val logoPath: String,
	@SerializedName("provider_id") val providerId: Int,
	@SerializedName("display_priority") val displayPriority: Int,
	@SerializedName("provider_name") val providerName: String
) : Parcelable

@Parcelize
data class BR(
	@SerializedName("buy") val buy: List<BuyItem>,
	@SerializedName("link") val link: String,
	@SerializedName("rent") val rent: List<RentItem>,
	@SerializedName("flatrate") val flatrate: List<FlatrateItem>?
) : Parcelable

@Parcelize
data class RentItem(
	@SerializedName("logo_path") val logoPath: String,
	@SerializedName("provider_id") val providerId: Int,
	@SerializedName("display_priority") val displayPriority: Int,
	@SerializedName("provider_name") val providerName: String
) : Parcelable