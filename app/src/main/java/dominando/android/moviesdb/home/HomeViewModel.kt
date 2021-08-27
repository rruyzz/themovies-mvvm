package dominando.android.moviesdb.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dominando.android.moviesdb.model.MovieResultResponse
import dominando.android.moviesdb.model.SeriesResultsResponse
import dominando.android.moviesdb.utils.api.Service
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(private val service: Service) : ViewModel() {

    private val movieState = MutableLiveData<HomeMovieList>()
    val movieViewState: LiveData<HomeMovieList> = movieState
    private var hasGet = false
    fun getAllMovies() {
        movieState.value = HomeMovieList.Loading(true)
        viewModelScope.launch {
            try {
                val resultSerie = service.getPopularSeries()
                val resultMovie = service.getPopularMovies()
                val resulTopSeries = service.getSoonMovies()
                movieState.value = HomeMovieList.Success(resultSerie, resultMovie, resulTopSeries)
                hasGet = true
            } catch (throwable: Exception) {
                movieState.value = HomeMovieList.Error(throwable.message ?: "Erro Desconhecido")
            } finally {
                movieState.value = HomeMovieList.Loading(false)
            }
        }
    }
}

sealed class HomeMovieList {
    class Success(val listSerie: SeriesResultsResponse,
                  val listMovie: MovieResultResponse,
                  val listTopSerie: MovieResultResponse) : HomeMovieList()
    class Loading(val isLoading: Boolean) : HomeMovieList()
    class Error(val error: String) : HomeMovieList()
}

