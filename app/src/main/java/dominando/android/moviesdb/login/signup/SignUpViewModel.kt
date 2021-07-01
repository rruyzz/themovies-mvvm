package dominando.android.moviesdb.login.signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SignUpViewModel() : ViewModel() {
    private var mAuth = FirebaseAuth.getInstance()
    var loginSuccess: MutableLiveData<Boolean> = MutableLiveData()
    var showLoad: MutableLiveData<Boolean> = MutableLiveData()

    fun loginEmail(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
    }

    fun getGoogleLogin(credential: String){
        val credential = GoogleAuthProvider.getCredential(credential, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                Log.i("TESTE", it.toString())
                loginSuccess.value = it.isSuccessful
            }
    }
}



