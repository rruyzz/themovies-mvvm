package dominando.android.moviesdb.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dominando.android.moviesdb.model.SearchResponse
import dominando.android.moviesdb.utils.api.Service
import kotlinx.coroutines.launch

class SearchViewModel(private val service: Service) : ViewModel() {
    private val searchState = MutableLiveData<SearchState>()
    val searchViewState: LiveData<SearchState> = searchState

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
}

sealed class SearchState{
    class Succcess(val success: SearchResponse) : SearchState()
    class Loading(val isLoading: Boolean) : SearchState()
    class Error(val error: String) : SearchState()
}