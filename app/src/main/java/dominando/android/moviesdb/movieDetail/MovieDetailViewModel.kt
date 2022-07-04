package dominando.android.moviesdb.movieDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dominando.android.moviesdb.model.MovieDetailResponse
import dominando.android.moviesdb.model.MovieProviderResponse
import dominando.android.moviesdb.model.MovieResultResponse
import dominando.android.moviesdb.utils.SafeRequest
import dominando.android.moviesdb.utils.api.Service
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val service: Service) : ViewModel() {

    private val _movieDetailState = MutableSharedFlow<MovieDetails>(0)
    val movieDetailState = _movieDetailState.asSharedFlow()
    val listMovies: MutableList<MovieDetail> = mutableListOf()
    var itemPosition = 0

    fun getMovieDetail(movieId: String) = viewModelScope.launch {
        _movieDetailState.emit(MovieDetails.Loading(true))
        val resultMovieDetailRequest = async { SafeRequest.safeApiCall { service.getMovieDetail(movieId) } }
        val provideMoviesRequest = async { SafeRequest.safeApiCall { service.getMoviesProviders(movieId) } }
        val similarMoviesRequest = async { SafeRequest.safeApiCall { service.getSimilarMovie(movieId) } }
        val resultMovieDetail = resultMovieDetailRequest.await()
        val provideMovies = provideMoviesRequest.await()
        val similarMovies = similarMoviesRequest.await()
        if (resultMovieDetail.isSuccess && provideMovies.isSuccess && similarMovies.isSuccess) {
            listMovies.add(MovieDetail(resultMovieDetail.successBody?.body(), provideMovies.successBody?.body(), similarMovies.successBody?.body()))
            _movieDetailState.emit(MovieDetails.Success(listMovies))
        } else _movieDetailState.emit(MovieDetails.Error("ErrorEmitido"))
        _movieDetailState.emit(MovieDetails.Loading(false))
    }
}

sealed class MovieDetails {
    class Success(val movie: List<MovieDetail>) : MovieDetails()
    class Loading(val isLoading: Boolean) : MovieDetails()
    class Error(val error: String) : MovieDetails()
}

data class MovieDetail(
    val detail: MovieDetailResponse?,
    val providers: MovieProviderResponse?,
    val similar: MovieResultResponse?
)