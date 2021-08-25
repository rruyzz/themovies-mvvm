package dominando.android.moviesdb.home

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import dominando.android.moviesdb.R
import dominando.android.moviesdb.databinding.FragmentListBinding
import dominando.android.moviesdb.model.DiscoveryListMovieResponse
import dominando.android.moviesdb.splash.SplashActivity
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(), HomeAdapter.onClick {

    private lateinit var mAuth: FirebaseAuth
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding : FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        setButtons()
        setObservers()
        viewModel.getAllMovies()
    }
    private fun setObservers() {
        viewModel.state.observe(requireActivity(), Observer {
            when(it){
                is HomeMovieList.Success -> setRecycler(it.response)
                is HomeMovieList.Error -> Toast.makeText(requireActivity() ,it.error, Toast.LENGTH_SHORT).show()
                is HomeMovieList.Loading -> renderLoading(it.isLoading)
            }
        })
    }

    private fun setRecycler(list: DiscoveryListMovieResponse){
        recycler_view_main.adapter= HomeAdapter(this ,list.results, requireContext())
        recycler_view_main.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
    }

    private fun renderLoading(isLoading: Boolean) = with(binding){
        progress.isVisible  = isLoading
    }
    private fun setButtons(){
        text_title.setOnClickListener {
            mAuth = FirebaseAuth.getInstance()
            mAuth.signOut()
            val intent = Intent(requireContext(), SplashActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        text_check_movies.setOnClickListener {
            Toast.makeText(requireContext(), "TESTO", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setView() {
        val spannable = SpannableStringBuilder(getString(R.string.text_title_list))
        spannable.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.red)),
            12,
            spannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text_title.text = spannable
    }

    override fun onClick() {
        TODO("Not yet implemented")
    }
}
