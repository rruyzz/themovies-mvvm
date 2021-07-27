package dominando.android.moviesdb.home

import dominando.android.moviesdb.model.DiscoveryListMovieResponse
import dominando.android.moviesdb.utils.api.Resources
import dominando.android.moviesdb.utils.api.ResponseHandler
import dominando.android.moviesdb.utils.api.Service
import java.lang.Exception

class HomeRepository(private val service: Service,
                     private val responseHandler : ResponseHandler) {

    suspend fun getWeather() : Resources<DiscoveryListMovieResponse> {
        return try {
            responseHandler.handleSuccess(service.getDiscoveryList())
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}

