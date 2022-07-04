package dominando.android.moviesdb.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dominando.android.moviesdb.R
import dominando.android.moviesdb.databinding.ActivityMainBinding
import dominando.android.moviesdb.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = savedInstanceState ?: intent.extras
        setupNavigationController(bundle)
//        setButtons()
//        visibilityBottomNav()
    }

    private fun setupNavigationController(bundle: Bundle?) {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.search_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.setGraph(R.navigation.search_graph)
    }


}