package dominando.android.moviesdb.login.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import dominando.android.moviesdb.MainActivity
import dominando.android.moviesdb.R
import dominando.android.moviesdb.databinding.FragmentLoginHomeBinding
import dominando.android.moviesdb.utils.firebase.firebase.saveUser


class LoginHomeFragment : Fragment() {

    lateinit var binding: FragmentLoginHomeBinding
    private val navigate get() = findNavController()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButton()
    }

    private fun setButton(){
        binding.buttonStart.setOnClickListener {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
//            navigate.navigate(LoginHomeFragmentDirections.actionLoginHomeFragmentToLoginFragment())
        }
    }
}