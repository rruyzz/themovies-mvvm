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
    var loginSuccess: MutableLiveData<Boolean> = MutableLiveData()
    var showLoad: MutableLiveData<Boolean> = MutableLiveData()
    var hasError: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var live: LiveData<Int>


    fun loginEmail(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
    }

    fun getGoogleLogin(credential: String) {
        showLoad.value = true
        val credential = GoogleAuthProvider.getCredential(credential, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                showLoad.value = false
                loginSuccess.value = it.isSuccessful
            }
    }

    fun getFacebookLogin(accessToken: AccessToken) {
        showLoad.value = true
        val credential = FacebookAuthProvider.getCredential(accessToken.token)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showLoad.value = false
                    loginSuccess.value = it.isSuccessful
                } else {
                    hasError.value = true
                }
            }
    }
}




