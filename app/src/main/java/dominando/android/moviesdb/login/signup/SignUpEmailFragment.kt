package dominando.android.moviesdb.login.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import dominando.android.moviesdb.R
import dominando.android.moviesdb.databinding.FragmentSignUpEmailBinding

class SignUpEmailFragment : Fragment() {

    lateinit var binding: FragmentSignUpEmailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpEmailBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }
}