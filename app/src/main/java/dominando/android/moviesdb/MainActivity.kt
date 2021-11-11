package dominando.android.moviesdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dominando.android.moviesdb.databinding.ActivityMainBinding

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
    }

    private fun setupNavigationController(bundle: Bundle?) {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.home_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.setGraph(R.navigation.main_graph)
    }

    private fun setButtons() = with(binding){
        icProfile.setOnClickListener { navController.setGraph(R.navigation.profile_graph) }
        icSearch.setOnClickListener { navController.setGraph(R.navigation.main_graph) }
    }
}
