package dominando.android.moviesdb.serieDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dominando.android.moviesdb.model.CastResponse
import dominando.android.moviesdb.model.MovieProviderResponse
import dominando.android.moviesdb.model.SerieResponse
import dominando.android.moviesdb.utils.SafeRequest
import dominando.android.moviesdb.utils.api.Service
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SerieDetailViewModel(private val service: Service) : ViewModel() {

    private val _serieDetailState = MutableSharedFlow<SerieDetailsState>(0)
    val serieDetailState = _serieDetailState.asSharedFlow()
    private var episodeDetail: SerieDetail? = null

    fun getSerieDetail(serieId: String) {
        viewModelScope.launch {
            _serieDetailState.emit(SerieDetailsState.Loading(true))
            val serieDetailResponse = async { SafeRequest.safeApiCall { service.getSerieDetail(serieId) } }
            val providersResponse = async { SafeRequest.safeApiCall { service.getSerieProviders(serieId) } }
            val castingResponse = async { SafeRequest.safeApiCall { service.getCast(serieId) } }
            val serieDetail = serieDetailResponse.await()
            val providers = providersResponse.await()
            val casting = castingResponse.await()
            if (serieDetail.isSuccess && providers.isSuccess && casting.isSuccess) {
                episodeDetail = SerieDetail(
                    serieDetail.successBody?.body(),
                    providers.successBody?.body(),
                    casting.successBody?.body()
                )
                _serieDetailState.emit(SerieDetailsState.Success(episodeDetail!!))
            }
            _serieDetailState.emit(SerieDetailsState.Loading(false))
        }
    }
}
sealed class SerieDetailsState {
    class Success(val serieDetail: SerieDetail) : SerieDetailsState()
    class Loading(val isLoading: Boolean) : SerieDetailsState()
    class Error(val error: String) : SerieDetailsState()
}

data class SerieDetail(
    val detail: SerieResponse?,
    val providers: MovieProviderResponse?,
    val casting: CastResponse?
)