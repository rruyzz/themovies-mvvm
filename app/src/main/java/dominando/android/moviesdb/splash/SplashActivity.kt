package dominando.android.moviesdb.splash

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import dominando.android.moviesdb.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setNav()
    }

    private fun setNav(){
        val myNavHostFragment : NavHostFragment = fragment as NavHostFragment
        val inflater = myNavHostFragment.navController.navInflater
        myNavHostFragment.navController.graph = inflater.inflate(R.navigation.login_graph)
    }
}