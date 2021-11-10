package dominando.android.moviesdb.utils.remoteConfig

import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

object RemoteConfig {

    fun remoteFetch(){
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate()
    }

    fun getRemoteString(key: String) = Firebase.remoteConfig(FirebaseApp.getInstance())[key].asString()

    val apiKey get() = getRemoteString("API_KEY")
    val baseUrl get() = getRemoteString("BASE_URL")
}