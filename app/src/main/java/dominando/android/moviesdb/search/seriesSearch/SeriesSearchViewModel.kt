package dominando.android.moviesdb.search.seriesSearch

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dominando.android.moviesdb.home.HomeMovieList
import dominando.android.moviesdb.model.MovieResultResponse
import dominando.android.moviesdb.model.SearchResponse
import dominando.android.moviesdb.model.SeriesResultsResponse
import dominando.android.moviesdb.utils.api.Service
import kotlinx.coroutines.launch

class SeriesSearchViewModel(private val service: Service) : ViewModel() {

//    private val seriesListState = MutableLiveData<SeriesSearchList>()
//    val seriesListViewState: LiveData<SeriesSearchList> = seriesListState
//    lateinit var resultSerie : SeriesResultsResponse
//    private var hasGet = false
//    var page = 2
//    fun getSeriesList() {
//        if(hasGet) {
//            seriesListState.value = SeriesSearchList.Success(resultSerie)
//            return
//        }
//        seriesListState.value = SeriesSearchList.Loading(true)
//        viewModelScope.launch {
//            try {
//                resultSerie = service.getDiscoverySeries(page)
//                seriesListState.value = SeriesSearchList.Success(resultSerie)
//                hasGet = true
//            } catch (throwable: Exception) {
//                Log.e("HomeViewModel", "exception", throwable)
//                seriesListState.value = SeriesSearchList.Error(throwable.message ?: "Erro Desconhecido")
//            } finally {
//                seriesListState.value = SeriesSearchList.Loading(false)
//            }
//        }
//    }
//}
}


sealed class SearchState{
    class Succcess(val success: SearchResponse) : SearchState()
    class Loading(val isLoading: Boolean) : SearchState()
    class Error(val error: String) : SearchState()
}