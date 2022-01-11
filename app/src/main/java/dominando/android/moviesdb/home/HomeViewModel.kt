package dominando.android.moviesdb.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dominando.android.moviesdb.model.MovieResultResponse
import dominando.android.moviesdb.model.SeriesResultsResponse
import dominando.android.moviesdb.utils.SafeRequest.safeApiCall
import dominando.android.moviesdb.utils.api.Service
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val service: Service) : ViewModel() {

    private val _moviesState = MutableSharedFlow<HomeMovieList>(0)
    val movieState = _moviesState.asSharedFlow()
    private lateinit var resultSerie: SeriesResultsResponse
    private lateinit var resultTopSeries: MovieResultResponse
    private lateinit var resultMovie: MovieResultResponse
    var hasSuccessGet = false
    fun getAllMovies() = viewModelScope.launch {
        if (hasSuccessGet) {
            _moviesState.emit(HomeMovieList.Success(resultSerie, resultMovie, resultTopSeries))
            return@launch
        }
        _moviesState.emit(HomeMovieList.Loading(true))
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
            _moviesState.emit(HomeMovieList.Success(resultSerie, resultMovie, resultTopSeries))
            hasSuccessGet = true
        } else _moviesState.emit(HomeMovieList.Error("ErrorEmitido"))
        _moviesState.emit(HomeMovieList.Loading(false))
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