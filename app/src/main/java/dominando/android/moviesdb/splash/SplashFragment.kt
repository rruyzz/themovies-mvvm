package dominando.android.moviesdb.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import dominando.android.moviesdb.MainActivity
import dominando.android.moviesdb.R
import dominando.android.moviesdb.login.SignUpActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {

    val scope = CoroutineScope(Dispatchers.Main)
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSplashTime()
    }

    private fun setSplashTime(){
        scope.launch {
            delay(3000)
            checkSignIn()
        }
    }

    private fun checkSignIn(){
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        if(user == null){
            Log.d("SIGN", "$user")
            val intent = Intent(requireContext(), SignUpActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        } else {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            Log.d("SIGN", "$user")
            requireActivity().finish()
        }
    }
}