package dominando.android.moviesdb.home

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import dominando.android.moviesdb.R
import dominando.android.moviesdb.model.DiscoveryListMovieItem
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        setRecycler()
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
        recycler_view_main.adapter= ListAdapter(setMovie(), requireContext())
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
}