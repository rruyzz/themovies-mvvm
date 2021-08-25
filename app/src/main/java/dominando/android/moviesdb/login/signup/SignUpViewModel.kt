package dominando.android.moviesdb.login.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.facebook.AccessToken
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SignUpViewModel() : ViewModel() {
    private var mAuth = FirebaseAuth.getInstance()
    var state: MutableLiveData<LoginState> = MutableLiveData()

    fun loginEmail(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
    }

    fun getGoogleLogin(credential: String) {
        state.value = LoginState.LOADING
        val credential = GoogleAuthProvider.getCredential(credential, null)
        mAuth.signInWithCredential(credential)
            .addOnFailureListener {
                state.value = LoginState.ERROR
            }
            .addOnSuccessListener {
                state.value = LoginState.SUCCESS
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


