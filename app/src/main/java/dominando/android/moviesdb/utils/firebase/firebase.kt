package dominando.android.moviesdb.utils.firebase

import android.util.Log
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dominando.android.moviesdb.adapters.MovieSerieItem
import dominando.android.moviesdb.model.MovieFirebase
import dominando.android.moviesdb.model.User
import dominando.android.moviesdb.model.Users
import dominando.android.moviesdb.search.SearchActivity

object firebase {

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

    fun Fragment.saveMovie(title: String, id: String, poster: String){
        val database = Firebase.database.reference
        val movie = MovieFirebase(id, true, title, poster)
        val mAuth = FirebaseAuth.getInstance()
        database.child("users").child(mAuth.currentUser?.uid ?: "QUEBROU").child("movie").push().setValue(movie)
    }

    fun Fragment.saveUser(){
        val database = Firebase.database.reference
        val mAuth = FirebaseAuth.getInstance()
        database.child("users").get().addOnSuccessListener {
            val post = it.getValue<Users>()
            val clientsId = post?.users?.map { user -> user?.id }
            if(clientsId?.contains(mAuth.currentUser?.uid ?: "") != false){
                val user = User(mAuth.currentUser?.displayName ?: "", mAuth.currentUser?.uid ?: "QUEBROU", listOf(), listOf())
                database.child("users").child(mAuth.currentUser?.uid ?: "QUEBROU").setValue(user)
            }
            Log.i("firebase", "Got value ${post}")
            Log.i("firebase", "Got value ${clientsId}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }
}