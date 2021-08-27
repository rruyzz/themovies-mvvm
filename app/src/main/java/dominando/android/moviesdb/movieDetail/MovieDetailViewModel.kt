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

    fun getMovieDetail(movieId: String) {
        movieDetailState.value = MovieDetails.Loading(true)
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                try {
                    val resultMovieDetail = service.getMovieDetail(movieId)
                    val provideMovies = service.getMoviesProviders(movieId)
                    val similarMovies = service.getSimilarMovie(movieId)
                    movieDetailState.value =
                        MovieDetails.Success(resultMovieDetail, provideMovies, similarMovies)
                } catch (throwable: Exception) {
                    Log.e("MYAPP", "exception", throwable);
                    movieDetailState.value = MovieDetails.Error("ERRPR")
                } finally {
                    movieDetailState.value = MovieDetails.Loading(false)
                }
            }
        }
    }
}

sealed class MovieDetails {
    class Success(
        val movieDetail: MovieDetailResponse,
        val providerResponse: MovieProviderResponse,
        val similarMovies: MovieResultResponse) : MovieDetails()
    class Loading(val isLoading: Boolean) : MovieDetails()
    class Error(val error: String) : MovieDetails()
}