package dominando.android.moviesdb.serieDetail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SerieDetailAdapter(fragmentManager: Fragment) : FragmentStateAdapter(fragmentManager) {

    private val fragmentList = ArrayList<Fragment>()

    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
    }

    override fun getItemCount() = fragmentList.size

    override fun createFragment(position: Int) =fragmentList[position]
}