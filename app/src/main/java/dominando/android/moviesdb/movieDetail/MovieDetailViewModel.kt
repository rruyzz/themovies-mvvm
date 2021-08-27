package dominando.android.moviesdb.movieDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dominando.android.moviesdb.model.MovieDetailResponse
import dominando.android.moviesdb.model.MovieProviderResponse
import dominando.android.moviesdb.model.MovieResultResponse
import dominando.android.moviesdb.utils.api.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailViewModel(private val service: Service) : ViewModel() {
    private val movieDetailState = MutableLiveData<MovieDetails>()
    val movieDetailViewState: LiveData<MovieDetails> = movieDetailState
    val listMovies : MutableList<MovieDetail> = mutableListOf()
    val listMoviesState get() = listMovies
    var itemPosition = 0
    fun getMovieDetail(movieId: String) {
        movieDetailState.value = MovieDetails.Loading(true)
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                try {
                    val resultMovieDetail = service.getMovieDetail(movieId)
                    val provideMovies = service.getMoviesProviders(movieId)
                    val similarMovies = service.getSimilarMovie(movieId)
                    listMovies.add(MovieDetail(resultMovieDetail, provideMovies, similarMovies))
                    movieDetailState.value = MovieDetails.Success(listMovies)
                } catch (throwable: Exception) {
                    Log.e("MoviesDetail", "exception", throwable);
                    movieDetailState.value = MovieDetails.Error(throwable.message ?: "Erro Desconhecido")
                } finally {
                    movieDetailState.value = MovieDetails.Loading(false)
                }
            }
        }
    }
}

sealed class MovieDetails {
    class Success(val movie: List<MovieDetail>) : MovieDetails()
    class Loading(val isLoading: Boolean) : MovieDetails()
    class Error(val error: String) : MovieDetails()
}

data class MovieDetail(
    val detail: MovieDetailResponse,
    val providers: MovieProviderResponse,
    val similar: MovieResultResponse
)