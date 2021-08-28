package dominando.android.moviesdb.serieDetail.serieSeasons

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dominando.android.moviesdb.databinding.FragmentSerieSeasonsBinding

class SerieSeasonsFragment : Fragment() {

    private lateinit var binding: FragmentSerieSeasonsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSerieSeasonsBinding.inflate(inflater, container, false)
        return binding.root
    }
}

