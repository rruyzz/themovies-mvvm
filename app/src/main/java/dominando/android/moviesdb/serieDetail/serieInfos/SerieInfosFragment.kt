package dominando.android.moviesdb.serieDetail.serieInfos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dominando.android.moviesdb.R
import dominando.android.moviesdb.databinding.FragmentSerieInfosBinding


class SerieInfosFragment : Fragment() {

    private lateinit var binding: FragmentSerieInfosBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSerieInfosBinding.inflate(inflater, container, false)
        return binding.root
    }
}
