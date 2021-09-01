package dominando.android.moviesdb.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dominando.android.moviesdb.model.MovieResultResponse
import dominando.android.moviesdb.model.SearchResponse
import dominando.android.moviesdb.model.SeriesResultsResponse
import dominando.android.moviesdb.utils.api.Service
import kotlinx.coroutines.launch

class SearchViewModel(private val service: Service) : ViewModel() {
    private val searchState = MutableLiveData<SearchState>()
    val searchViewState: LiveData<SearchState> = searchState
    private val discoveryListState = MutableLiveData<SeriesSearchList>()
    val discoveryListViewState: LiveData<SeriesSearchList> = discoveryListState
    private lateinit var resultSerie : SeriesResultsResponse
    private lateinit var resultMovie : MovieResultResponse
    private var hasGet = false
    var page = 2

    fun searchMovie(text: String){
        searchState.value = SearchState.Loading(true)
        viewModelScope.launch {
            try{
                val response = service.getSearch(text)
                searchState.value = SearchState.Succcess(response)
            } catch (e: java.lang.Exception){
                Log.e("HomeViewModel", "exception", e);
                searchState.value = SearchState.Error(e.message ?: "Erro Desconhecido")
            }finally {
                searchState.value = SearchState.Loading(false)
            }
        }
    }
    fun getDiscoveyList() {
        if(hasGet) {
            discoveryListState.value = SeriesSearchList.Success(resultSerie, resultMovie)
            return
        }
        discoveryListState.value = SeriesSearchList.Loading(true)
        viewModelScope.launch {
            try {
                resultSerie = service.getDiscoverySeries(page)
                resultMovie = service.getDiescoveryMovies(page)
                discoveryListState.value = SeriesSearchList.Success(resultSerie, resultMovie)
                hasGet = true
            } catch (throwable: Exception) {
                Log.e("HomeViewModel", "exception", throwable)
                discoveryListState.value = SeriesSearchList.Error(throwable.message ?: "Erro Desconhecido")
            } finally {
                discoveryListState.value = SeriesSearchList.Loading(false)
            }
        }
    }
}

sealed class SearchState{
    class Succcess(val success: SearchResponse) : SearchState()
    class Loading(val isLoading: Boolean) : SearchState()
    class Error(val error: String) : SearchState()
}

sealed class SeriesSearchList {
    class Success(val listSerie: SeriesResultsResponse, val listMovie: MovieResultResponse) : SeriesSearchList()
    class Loading(val isLoading: Boolean) : SeriesSearchList()
    class Error(val error: String) : SeriesSearchList()
}
