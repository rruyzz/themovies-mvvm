package dominando.android.moviesdb.home

import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dominando.android.moviesdb.model.DiscoveryListMovieResponse
import dominando.android.moviesdb.utils.api.Resources
import dominando.android.moviesdb.utils.api.Service
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel(){

    val weather: LiveData<Resources<DiscoveryListMovieResponse>> = liveData {
        emit(repository.getWeather())
    }

}