package dominando.android.moviesdb.home

import dominando.android.moviesdb.model.DiscoveryListMovieResponse
import dominando.android.moviesdb.utils.Constanst
import dominando.android.moviesdb.utils.Constanst.API_KEY
import dominando.android.moviesdb.utils.api.Service
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class HomeRepository(private val service: Service) {

    suspend fun getMovies() = service.getDiscoveryList(API_KEY)

//        suspend fun getGithubAccountAsync(): Deferred<DiscoveryListMovieResponse?> {
//            return withContext(Dispatchers.IO) {
//                async {
//                    try {
//                        // for demo purpose, hence no error checking
//                        service.getDiscoveryList(Constanst.API_KEY).await().body() as DiscoveryListMovieResponse
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                        null
//                    }
//                }
//            }

}

