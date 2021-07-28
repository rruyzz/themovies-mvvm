package dominando.android.moviesdb.home

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import dominando.android.moviesdb.R
import dominando.android.moviesdb.databinding.FragmentListBinding
import dominando.android.moviesdb.model.DiscoveryListMovieItem
import dominando.android.moviesdb.model.DiscoveryListMovieResponse
import dominando.android.moviesdb.splash.SplashActivity
import dominando.android.moviesdb.utils.api.Status
import kotlinx.android.synthetic.main.fragment_list.*


class HomeFragment : Fragment(), HomeAdapter.onClick {

    private lateinit var mAuth: FirebaseAuth
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding : FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        setRecycler()
        setButtons()

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

    private fun setRecycler(){
        recycler_view_main.adapter= HomeAdapter(this ,setMovie(), requireContext())
        recycler_view_main.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
    }

    private fun setMovie(): List<DiscoveryListMovieItem> {
        val superbad = DiscoveryListMovieItem("",
            "",
            "Super Bad",
            true,
            "SUPER BAD",
            listOf(21),
            "",
            "",
            "",
            10.0,9.0, 21, true, 10)
        val batman = DiscoveryListMovieItem("",
            "",
            "BATMAN",
            true,
            "BATMAN",
            listOf(21),
            "",
            "",
            "",
            10.0,8.0, 21, true, 10)
        return listOf(superbad, batman, batman, superbad, superbad, batman, batman, superbad, superbad, batman, batman, superbad, superbad, batman, batman, superbad)
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

    override fun onClick() {
        TODO("Not yet implemented")
    }
}
