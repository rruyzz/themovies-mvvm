package dominando.android.moviesdb.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dominando.android.moviesdb.model.DiscoveryMovieResponse
import dominando.android.moviesdb.model.DiscoverySerieResponse
import dominando.android.moviesdb.utils.Constanst.API_KEY
import dominando.android.moviesdb.utils.api.Service
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(private val service: Service) : ViewModel() {

    private val movieState = MutableLiveData<HomeMovieList>()
    val movieViewState: LiveData<HomeMovieList> = movieState

    fun getAllMovies() {
        movieState.value = HomeMovieList.Loading(true)
        viewModelScope.launch {
            delay(3000)
            try {
                val resultSerie = service.getDiscoverySerieList(API_KEY)
                val resultMovie = service.getDiscoveryMovieList(API_KEY)
                movieState.value = HomeMovieList.Success(resultSerie, resultMovie)
            } catch (throwable: Exception) {
                movieState.value = HomeMovieList.Error(throwable.message ?: "Erro Desconhecido")
            } finally {
                movieState.value = HomeMovieList.Loading(false)
            }
        }
    }
}

sealed class HomeMovieList {
    class Success(val listSerie: DiscoverySerieResponse, val listMovie: DiscoveryMovieResponse) : HomeMovieList()
    class Loading(val isLoading: Boolean) : HomeMovieList()
    class Error(val error: String) : HomeMovieList()
}

