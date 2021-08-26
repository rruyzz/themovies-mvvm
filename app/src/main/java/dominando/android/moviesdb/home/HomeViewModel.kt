package dominando.android.moviesdb.home

import android.telecom.Call
import androidx.lifecycle.*
import dominando.android.moviesdb.login.signup.LoginState
import dominando.android.moviesdb.model.DiscoveryListMovieResponse
import dominando.android.moviesdb.utils.Constanst.API_KEY
import dominando.android.moviesdb.utils.api.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.security.auth.callback.Callback

class HomeViewModel(private val service: Service) : ViewModel() {
    private val _moviesList = MutableLiveData<DiscoveryListMovieResponse>()
    val moviesList: LiveData<DiscoveryListMovieResponse> get() = _moviesList
    var state: MutableLiveData<HomeMovieList> = MutableLiveData()

    fun getAllMovies() {
        state.postValue(HomeMovieList.Loading(true))
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val result = service.getDiscoveryList(API_KEY)
                    state.postValue(HomeMovieList.Success(result))
                    _moviesList.postValue(result)
                } catch (throwable: Exception) {
                    state.postValue(HomeMovieList.Error(throwable.message ?: "Erro Desconhecido"))
                }
            }
            state.postValue(HomeMovieList.Loading(false))
        }
    }
}

sealed class HomeMovieList{
    class Success(val response: DiscoveryListMovieResponse) : HomeMovieList()
    class Loading(val isLoading: Boolean) : HomeMovieList()
    class Error(val error: String) : HomeMovieList()
}

