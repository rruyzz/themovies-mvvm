package dominando.android.moviesdb.login.signup

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.facebook.AccessToken
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class SignUpViewModel() : ViewModel() {
    private var mAuth = FirebaseAuth.getInstance()
    var state: MutableLiveData<LoginState> = MutableLiveData()

    fun loginEmail(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
    }

    fun getGoogleLogin(idToken: String) {
        state.value = LoginState.LOADING
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnFailureListener {
                state.value = LoginState.ERROR
            }
            .addOnSuccessListener {
//                Log.d(TAG, "User profile updated.")
                state.value = LoginState.SUCCESS

//                updateProfile(it.user?.displayName, it.user?.photoUrl)
            }
    }

    private fun updateProfile(name: String?, photo: Uri?){
        val user = mAuth.currentUser
        val profileUpdates = userProfileChangeRequest {
            displayName = name + "RUIZ"
            photoUri = photo
        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile updated.")
                    state.value = LoginState.SUCCESS
                }
            }
    }

    fun getFacebookLogin(accessToken: AccessToken) {
        state.value = LoginState.LOADING
        val credential = FacebookAuthProvider.getCredential(accessToken.token)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    state.value = LoginState.SUCCESS
                } else {
                    state.value = LoginState.ERROR
                }
            }
    }
}

enum class LoginState {
    SUCCESS,
    LOADING,
    ERROR
}


