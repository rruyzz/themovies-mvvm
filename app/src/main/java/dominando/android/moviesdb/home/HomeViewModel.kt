package dominando.android.moviesdb.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dominando.android.moviesdb.model.MovieResultResponse
import dominando.android.moviesdb.model.SeriesResultsResponse
import dominando.android.moviesdb.utils.api.Service
import kotlinx.coroutines.launch

class HomeViewModel(private val service: Service) : ViewModel() {

    private val movieState = MutableLiveData<HomeMovieList>()
    val movieViewState: LiveData<HomeMovieList> = movieState
    private lateinit var resultSerie : SeriesResultsResponse
    private lateinit var resultMovie : MovieResultResponse
    private lateinit var resulTopSeries : MovieResultResponse
    private var hasGet = false
    fun getAllMovies() {
        if(hasGet) {
            movieState.value = HomeMovieList.Success(resultSerie, resultMovie, resulTopSeries)
            return
        }
        movieState.value = HomeMovieList.Loading(true)
        viewModelScope.launch {
            try {
                resultSerie = service.getPopularSeries()
                resultMovie = service.getPopularMovies()
                resulTopSeries = service.getSoonMovies()
                movieState.value = HomeMovieList.Success(resultSerie, resultMovie, resulTopSeries)
                hasGet = true
            } catch (throwable: Exception) {
                Log.e("HomeViewModel", "exception", throwable);
                movieState.value = HomeMovieList.Error(throwable.message ?: "Erro Desconhecido")
            } finally {
                movieState.value = HomeMovieList.Loading(false)
            }
        }
    }

    fun searchMovie(text: String){
        viewModelScope.launch {
            try{
                service.getSearch(text)
            } catch (e: java.lang.Exception){
                Log.e("HomeViewModel", "exception", e);
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
