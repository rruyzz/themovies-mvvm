package dominando.android.moviesdb.login.signup

import android.content.Context
import android.content.Intent
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import dominando.android.moviesdb.MainActivity
import dominando.android.moviesdb.R
import dominando.android.moviesdb.extensions.showToast
import java.lang.Exception

class SignUpViewModel : ViewModel() {
    private var mAuth = FirebaseAuth.getInstance()
    val isSuccess: MutableLiveData<FirebaseUser> = MutableLiveData()
    fun loginEmail(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
    }z

    suspend fun getGoogleLogin(requestCode: Int, data: Intent?, context: Context){
        return if (requestCode == 120) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)!!
            showToast(context, account.id.toString())
            val credential = GoogleAuthProvider.getCredential(account.id, null)
            return try {
                mAuth.signInWithCredential(credential).addOnCompleteListener({
                    if(it.isSuccessful){
                        isSuccess.postValue(it)
                    }
                })
            }
        } else {
            return
        }
    }

    fun loginGoogleResult(idToken: String): Task<AuthResult> {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return mAuth.signInWithCredential(credential)
    }
}