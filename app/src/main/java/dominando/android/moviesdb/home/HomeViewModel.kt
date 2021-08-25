package dominando.android.moviesdb.home

import android.telecom.Call
import androidx.lifecycle.*
import dominando.android.moviesdb.model.DiscoveryListMovieResponse
import dominando.android.moviesdb.utils.api.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.security.auth.callback.Callback

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {
    private val _items = MutableLiveData<DiscoveryListMovieResponse>()
    val items: LiveData<DiscoveryListMovieResponse> get() = _items


    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    val errorMessage = MutableLiveData<String>()
    fun getAllMovies() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val result = repository.getMovies()
                    _items.postValue(result)
                } catch (throwable: Throwable) {
                    when (throwable) {
                        is IOException -> {
                            errorMessage.postValue("Network Error")
                        }
                        is HttpException -> {
                            val codeError = throwable.code()
                            val errorMessageResponse = throwable.message()
                            errorMessage.postValue("Error $errorMessageResponse : $codeError")
                        }
                        else -> {
                            errorMessage.postValue("Uknown error")
                        }
                    }
                }
            }
        }
    }

    val allUsers: LiveData<DiscoveryListMovieResponse> = liveData {
        val retrivedTodo = repository.getMovies()
        emit(retrivedTodo)
    }
}

//    fun getAllMovies() {
//        viewModelScope.launch {
//            val items = repository.getGithubAccountAsync().await()
//            items?.let {
//                _items.value = it
//            }
//        }
//    }

