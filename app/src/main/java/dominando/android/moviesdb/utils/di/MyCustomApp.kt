package dominando.android.moviesdb.utils.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import dominando.android.moviesdb.home.HomeRepository
import dominando.android.moviesdb.home.HomeViewModel
import dominando.android.moviesdb.login.signup.SignUpViewModel
import dominando.android.moviesdb.utils.Constanst.API_URL
import dominando.android.moviesdb.utils.api.Service
import dominando.android.moviesdb.utils.api.retrofitBuilder
import dominando.android.moviesdb.utils.api.retrofitHttpClient
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class MyCustomApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val appModules = listOf(repositoryModule, viewModels, apiModule, retrofitModule)
        startKoin {
            androidLogger()
            androidContext(this@MyCustomApp)
            modules(appModules)
        }

    }

    val apiModule = module {
        single(createdAtStart = false) { get<Retrofit>().create(Service::class.java) }
    }
    val repositoryModule = module {
        single { HomeRepository(get()) }
    }

    val viewModels = module {
        viewModel { SignUpViewModel() }
        viewModel { HomeViewModel(get()) }
    }

    val retrofitModule = module {
        single { Cache(androidApplication().cacheDir, 10L * 1024 * 1024) }
        single { GsonBuilder().create() }
        single { retrofitHttpClient() }
        single { retrofitBuilder() }
        single {
            Interceptor { chain ->
                chain.proceed(chain.request().newBuilder().apply {
                    header("Accept", "application/json")
                }.build())
            }
        }
    }
}
