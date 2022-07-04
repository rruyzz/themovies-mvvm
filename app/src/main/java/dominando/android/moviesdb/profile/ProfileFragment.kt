package dominando.android.moviesdb.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import dominando.android.moviesdb.R
import dominando.android.moviesdb.databinding.FragmentProfileBinding
import dominando.android.moviesdb.splash.SplashActivity
import dominando.android.moviesdb.utils.Constanst


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtons()
    }

    private fun setButtons() = with(binding) {
        mAuth = FirebaseAuth.getInstance()
        Glide.with(this@ProfileFragment).load(mAuth.currentUser?.photoUrl).into(imageProfile)
        icSettings.setOnClickListener {
            Log.d("TESTE", "Click: ")
            logOut()
        }
        imageProfile.setOnClickListener {
            Log.d("TESTE", "Click: ")
            logOut()
        }
        textName.text = mAuth.currentUser?.displayName ?: "ERROR"
    }

    private fun logOut() {
        Log.d("TESTE", "LgOut: ")
        teste()
        mAuth = FirebaseAuth.getInstance()
        mAuth.signOut()
        val intent = Intent(requireContext(), SplashActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun teste() {
        mAuth = FirebaseAuth.getInstance()
        val profile = mAuth.currentUser


        Log.d("TESTE", "Profile: ${profile?.email}")
        Log.d("TESTE", "Email: ${profile?.email}")
        Log.d("TESTE", "Phone: ${profile?.phoneNumber}")
        Log.d("TESTE", "Nome: ${profile?.displayName}")
        Log.d("TESTE", "Foto: ${profile?.photoUrl}")
    }
}
