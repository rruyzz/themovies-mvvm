package dominando.android.moviesdb.home

import android.telecom.Call
import androidx.lifecycle.*
import dominando.android.moviesdb.login.signup.LoginState
import dominando.android.moviesdb.model.DiscoveryListMovieResponse
import dominando.android.moviesdb.utils.api.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.security.auth.callback.Callback

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {
    private val _moviesList = MutableLiveData<DiscoveryListMovieResponse>()
    val moviesList: LiveData<DiscoveryListMovieResponse> get() = _moviesList
    var state: MutableLiveData<HomeMovieList> = MutableLiveData()

    fun getAllMovies() {
        state.value = HomeMovieList.Loading(true)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val result = repository.getMovies()
                    state.postValue(HomeMovieList.Success(result))
                    _moviesList.postValue(result)
                } catch (throwable: Throwable) {
                    state.postValue(HomeMovieList.Error(throwable.message ?: "Erro Desconhecido"))
                }
            }
        }
    }
}

sealed class HomeMovieList{
    class Success(val response: DiscoveryListMovieResponse) : HomeMovieList()
    class Loading(val isLoading: Boolean) : HomeMovieList()
    class Error(val error: String) : HomeMovieList()
}

