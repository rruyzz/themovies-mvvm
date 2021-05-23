package dominando.android.moviesdb.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dominando.android.moviesdb.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {

    val navigation get() = findNavController()
    val scope = CoroutineScope(Dispatchers.Main)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSplashTime()
    }

    private fun setSplashTime(){
        scope.launch {
            delay(3000)
            navigation()
        }
    }

    private fun navigation(){
        navigation.navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
    }
}