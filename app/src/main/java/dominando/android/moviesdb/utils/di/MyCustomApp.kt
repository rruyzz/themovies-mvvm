package dominando.android.moviesdb.utils.di

import android.app.Application
import com.google.gson.GsonBuilder
import dominando.android.moviesdb.home.HomeViewModel
import dominando.android.moviesdb.login.signup.SignUpViewModel
import dominando.android.moviesdb.utils.api.Service
import dominando.android.moviesdb.utils.api.retrofitBuilder
import dominando.android.moviesdb.utils.api.retrofitHttpClient
import okhttp3.Cache
import okhttp3.Interceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit

class MyCustomApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val appModules = listOf(viewModels, apiModule, retrofitModule)
        startKoin {
            androidLogger()
            androidContext(this@MyCustomApp)
            modules(appModules)
        }

    }

    val apiModule = module {
        single(createdAtStart = false) { get<Retrofit>().create(Service::class.java) }
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
