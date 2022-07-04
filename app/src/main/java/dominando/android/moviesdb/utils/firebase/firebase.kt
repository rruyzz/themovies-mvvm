package dominando.android.moviesdb.utils.firebase

import android.util.Log
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dominando.android.moviesdb.model.MovieFirebase
import dominando.android.moviesdb.model.User
import dominando.android.moviesdb.model.MovieListFirebase
import dominando.android.moviesdb.model.Users

object firebase {

    val mAuth = FirebaseAuth.getInstance()
    val userId = mAuth.currentUser?.uid ?: ""
    val apiKey get() = getRemoteString("API_KEY")
    val baseUrl get() = getRemoteString("BASE_URL")
    val database = Firebase.database.reference
    lateinit var moviesWatched : MutableList<MovieFirebase?>
//    var user = User()

    fun remoteFetch() {
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings { minimumFetchIntervalInSeconds = 3600 }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate()
        getMovies()
    }

    fun getRemoteString(key: String) = Firebase.remoteConfig(FirebaseApp.getInstance())[key].asString()

    fun Fragment.saveMovie(title: String, id: String, poster: String) {
        val movie = MovieFirebase(id, true, title, poster)
        database.child("users").child(userId).child("movie").push().setValue(movie)
    }

    fun Fragment.getUser(): User {
        var user = User()
        database.child("users").child(userId).get().addOnSuccessListener {
            user = it.getValue<User>() ?: User("fldks")
        }.addOnFailureListener {
            user = User("ERROU")
            Log.e("firebase", "Error getting data", it)
        }
        return user
    }
    fun Fragment.getMovie(){
        database.child("users").child(userId).get().addOnSuccessListener {
            val post = it.getValue<Users>()
            var list = post
        }
    }
    fun Fragment.getUsers() {
        database.child("users").get().addOnSuccessListener {
            val post = it.getValue<Users>()
            var list = post
//            val clientsId = post?.users?.map { user -> user?.id }
//            if (clientsId?.contains(mAuth.currentUser?.uid ?: "") != false) {
//                val user = User(
//                    mAuth.currentUser?.displayName ?: "",
//                    mAuth.currentUser?.uid ?: "QUEBROU",
//                    listOf(),
//                    listOf()
//                )
//                database.child("users").child(mAuth.currentUser?.uid ?: "QUEBROU").setValue(user)
//            }
//            Log.i("firebase", "Got value ${post}")
//            Log.i("firebase", "Got value ${clientsId}")
//        }.addOnFailureListener {
//            Log.e("firebase", "Error getting data", it)
        }
    }

    fun getMovies(): List<MovieFirebase> {
        moviesWatched = mutableListOf()
        var list: MutableList<MovieFirebase?> = mutableListOf<MovieFirebase?>()
//        database.child("users").child(userId).child("movie").get().addOnSuccessListener { snap ->
//            list = snap.children.map { i -> i.getValue(MovieFirebase::class.java) }
//            for(i in snap.children) {
//                list.add(i.getValue<MovieFirebase>(MovieFirebase::class.java))
//            }
//        }
        list.toList().filterNotNull()
        return list.toList().filterNotNull()
    }

    fun Fragment.saveUser() {
        database.child("users").get().addOnSuccessListener {
            val post = it.getValue<Users>()
            val clientsId = post?.users?.map { user -> user?.id }
            if (clientsId?.contains(mAuth.currentUser?.uid ?: "") != false) {
                val user = User(
                    mAuth.currentUser?.displayName ?: "",
                    mAuth.currentUser?.uid ?: "QUEBROU",
                    listOf(),
                    listOf()
                )
                database.child("users").child(mAuth.currentUser?.uid ?: "QUEBROU").setValue(user)
            }
            Log.i("firebase", "Got value ${post}")
            Log.i("firebase", "Got value ${clientsId}")
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }
    }
}