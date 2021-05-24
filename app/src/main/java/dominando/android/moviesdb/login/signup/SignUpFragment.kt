package dominando.android.moviesdb.login.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dominando.android.moviesdb.R
import dominando.android.moviesdb.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {

    private val navigation get() = findNavController()
    lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtons()
    }

    private fun setButtons() {
        binding.textViewMoreOptions.setOnClickListener {
            navigation.navigate(SignUpFragmentDirections.actionLoginFragmentToSignUpEmailFragment())
        }
    }

}