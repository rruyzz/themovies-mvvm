package dominando.android.moviesdb.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class Repository {

    private var mAuth = FirebaseAuth.getInstance()


    fun loginGoogleResult(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener({ it ->
                if(it.isSuccessful){

                }
            })
    }

}