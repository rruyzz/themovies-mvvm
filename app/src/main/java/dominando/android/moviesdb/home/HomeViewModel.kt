package dominando.android.moviesdb.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dominando.android.moviesdb.model.MovieResultResponse
import dominando.android.moviesdb.model.SeriesResultsResponse
import dominando.android.moviesdb.utils.ResultWrapper
import dominando.android.moviesdb.utils.SafeRequest.safeApiCall
import dominando.android.moviesdb.utils.api.Service
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val service: Service) : ViewModel() {

    private val movieState: MutableSharedFlow<HomeMovieList?> = MutableSharedFlow(1)
    private val _state: MutableStateFlow<StateFlowClass?> = MutableStateFlow(null)
    val state: StateFlow<StateFlowClass?> = _state
    val movieViewState: SharedFlow<HomeMovieList?> = movieState
    private lateinit var resultSerie: SeriesResultsResponse
    private lateinit var resultTopSeries: MovieResultResponse
    private lateinit var resultMovie: MovieResultResponse
    var hasSuccessGet = false
    fun getAllMovies() = viewModelScope.launch {
        if (hasSuccessGet) {
            movieState.emit(HomeMovieList.Success(resultSerie, resultMovie, resultTopSeries))
            return@launch
        }
        movieState.emit(HomeMovieList.Loading(true))
        val callSerie = async { safeApiCall { service.getPopularSeries() } }
        val callMovie = async { safeApiCall { service.getPopularMovies() } }
        val callTopSeries = async { safeApiCall { service.getSoonMovies() } }
        val responseSerie = callSerie.await()
        val responseMovie = callMovie.await()
        val responseTopSeries = callTopSeries.await()
        if (responseSerie.isSuccess && responseMovie.isSuccess && responseTopSeries.isSuccess) {
            resultSerie = responseSerie.successBody?.body()!!
            resultMovie = responseMovie.successBody?.body()!!
            resultTopSeries = responseTopSeries.successBody?.body()!!
            movieState.emit(HomeMovieList.Success(resultSerie, resultMovie, resultTopSeries))
            hasSuccessGet = true
        } else movieState.emit(HomeMovieList.Error("ErrorEmitido"))
        movieState.emit(HomeMovieList.Loading(false))
    }


    fun searchMovie() {
        viewModelScope.launch {
            _state.emit(StateFlowClass.Loading(true))
            val request = safeApiCall { service.getSoonsMovies() }
            when (request) {
                is ResultWrapper.Success -> _state.emit(StateFlowClass.SuccessFlow(request.value.body()!!))
                is ResultWrapper.Error -> _state.emit(StateFlowClass.Erro)
                is ResultWrapper.UnknownError -> _state.emit(StateFlowClass.Erro)
            }
            _state.emit(StateFlowClass.Loading(false))
        }
    }
}

sealed class HomeMovieList {
    class Success(
        val listSerie: SeriesResultsResponse,
        val listMovie: MovieResultResponse,
        val listTopSerie: MovieResultResponse
    ) : HomeMovieList()
    class Loading(val isLoading: Boolean) : HomeMovieList()
    class Error(val error: String) : HomeMovieList()
}

sealed class StateFlowClass {
    class SuccessFlow(val response: MovieResultResponse) : StateFlowClass()
    class Loading(val loading: Boolean) : StateFlowClass()
    object Erro : StateFlowClass()
}