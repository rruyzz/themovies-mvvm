package dominando.android.moviesdb.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dominando.android.moviesdb.model.MovieResultResponse
import dominando.android.moviesdb.model.SearchResponse
import dominando.android.moviesdb.model.SeriesResultsResponse
import dominando.android.moviesdb.utils.SafeRequest.safeApiCall
import dominando.android.moviesdb.utils.api.Service
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SearchViewModel(private val service: Service) : ViewModel() {

    private val _searchState = MutableSharedFlow<SearchState>(0)
    private val _discoveryListState = MutableSharedFlow<SeriesSearchList>(0)
    val searchViewState =_searchState.asSharedFlow()
    val discoveryListState = _discoveryListState.asSharedFlow()
    private lateinit var resultSerie: SeriesResultsResponse
    private lateinit var resultMovie: MovieResultResponse
    private var hasGet = false
    var page = 2

    fun searchMovie(text: String) {
        viewModelScope.launch {
            _searchState.emit(SearchState.Loading(true))
            val response = safeApiCall { service.getSearch(text) }
            if (response.isSuccess) {
                _searchState.emit(SearchState.Succcess(response.successBody?.body()!!))
            }  else {
                _searchState.emit(SearchState.Error(response.successBody?.body()!!.toString()))
            }
            _searchState.emit(SearchState.Loading(false))
        }
    }

    fun getDiscoveyList() = viewModelScope.launch {
        if (hasGet) {
            _discoveryListState.emit(SeriesSearchList.Success(resultSerie, resultMovie))
            return@launch
        }
        _discoveryListState.emit(SeriesSearchList.Loading(true))
        val resultSerieResponse = async { safeApiCall { service.getDiscoverySeries(page) } }
        val resultMovieResponse = async { safeApiCall { service.getDiscoveryMovies(page) } }
        val responseSerie = resultSerieResponse.await()
        val responseMovie = resultMovieResponse.await()
        if (responseSerie.isSuccess && responseMovie.isSuccess) {
            resultSerie = responseSerie.successBody?.body()!!
            resultMovie = responseMovie.successBody?.body()!!
            _discoveryListState.emit(SeriesSearchList.Success(resultSerie, resultMovie))
        } else {
            _discoveryListState.emit(SeriesSearchList.Error("Erro Desconhecido"))
        }
        _discoveryListState.emit(SeriesSearchList.Loading(false))
    }
}

sealed class SearchState {
    class Succcess(val success: SearchResponse) : SearchState()
    class Loading(val isLoading: Boolean) : SearchState()
    class Error(val error: String) : SearchState()
}

sealed class SeriesSearchList {
    class Success(val listSerie: SeriesResultsResponse, val listMovie: MovieResultResponse) : SeriesSearchList()
    class Loading(val isLoading: Boolean) : SeriesSearchList()
    class Error(val error: String) : SeriesSearchList()
}
