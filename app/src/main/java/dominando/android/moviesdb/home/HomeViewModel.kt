package dominando.android.moviesdb.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dominando.android.moviesdb.model.DiscoveryListMovieResponse
import dominando.android.moviesdb.utils.api.Service
import kotlinx.coroutines.launch

class HomeViewModel(private val service: Service) : ViewModel(){

    val listMoviews = MutableLiveData<DiscoveryListMovieResponse>()
    fun getMovie() = viewModelScope.launch {
        val response = service.getDiscoveryList()

    }
}