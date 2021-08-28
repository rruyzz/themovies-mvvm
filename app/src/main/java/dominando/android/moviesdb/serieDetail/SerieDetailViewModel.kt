package dominando.android.moviesdb.serieDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dominando.android.moviesdb.model.CastResponse
import dominando.android.moviesdb.model.MovieProviderResponse
import dominando.android.moviesdb.model.SerieDetailResponse
import dominando.android.moviesdb.movieDetail.MovieDetails
import dominando.android.moviesdb.utils.api.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class SerieDetailViewModel(private val service: Service) : ViewModel() {
    private val serieDetailState = MutableLiveData<SerieDetailsState>()
    val serieDetailViewState: LiveData<SerieDetailsState> = serieDetailState

    fun getSerieDetail(serieId: String){
        serieDetailState.value = SerieDetailsState.Loading(true)
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                try{
                    val serieDetail = service.getSerieDetail(serieId)
                    val providers = service.getSerieProviders(serieId)
                    val casting = service.getCast(serieId)
                    val response = SerieDetail(serieDetail, providers, casting)
                    serieDetailState.value = SerieDetailsState.Success(response)
                } catch (e: Exception){
                    Log.e("MoviesDetail", "exception", e)
                    serieDetailState.value = SerieDetailsState.Error(e.message ?: "Erro Desconhecido")
                } finally {
                    serieDetailState.value = SerieDetailsState.Loading(false)
                }
            }
        }
    }
}

sealed class SerieDetailsState {
    class Success(val serieDetail: SerieDetail) : SerieDetailsState()
    class Loading(val isLoading: Boolean) : SerieDetailsState()
    class Error(val error: String) : SerieDetailsState()
}

data class SerieDetail(
    val detail: SerieDetailResponse,
    val providers: MovieProviderResponse,
    val similar: CastResponse
)