package dominando.android.moviesdb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import bolts.AppLinkNavigation.navigate
import dominando.android.moviesdb.databinding.ActivityMainBinding
import dominando.android.moviesdb.home.HomeFragmentDirections
import dominando.android.moviesdb.search.SearchActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = savedInstanceState ?: intent.extras
        setupNavigationController(bundle)
        setButtons()
        visibilityBottomNav()
    }

    override fun onBackPressed() {
        val listFragmentsHome = listOf(R.id.profileFragment, R.id.moviesWatchedFragment, R.id.searchFragment2)
        if(navController.currentDestination?.id in listFragmentsHome){
            navController.setGraph(R.navigation.main_graph)
        } else {
            super.onBackPressed()
        }
    }
    private fun setupNavigationController(bundle: Bundle?) {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.home_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.setGraph(R.navigation.main_graph)
    }

    private fun setButtons() = with(binding){
        icProfile.setOnClickListener { navController.setGraph(R.navigation.profile_graph) }
        icSearch.setOnClickListener { navController.setGraph(R.navigation.search_graph) }
        icMovies.setOnClickListener{ navController.setGraph(R.navigation.watched_movies_graph) }
    }

    private fun visibilityBottomNav() = with(binding){
        val listFragmentsHome = listOf(R.id.listFragment, R.id.profileFragment, R.id.moviesWatchedFragment, R.id.searchFragment2)
        navController.addOnDestinationChangedListener { _, destination,_ ->
            linearBottom.isVisible = destination.id in listFragmentsHome
        }
    }
}
