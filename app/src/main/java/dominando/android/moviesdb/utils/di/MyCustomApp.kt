package dominando.android.moviesdb.utils.di

import android.app.Application
import dominando.android.moviesdb.home.HomeViewModel
import dominando.android.moviesdb.login.signup.SignUpViewModel
import dominando.android.moviesdb.utils.api.Service
import dominando.android.moviesdb.utils.di.Retrofit.okHttp
import dominando.android.moviesdb.utils.di.Retrofit.retrofit
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyCustomApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val appModules = listOf(viewModels, network)
        startKoin {
            modules(appModules)
            androidLogger()
            androidContext(this@MyCustomApp)
        }
    }

    val network = module {
        single{ retrofit() }
        single{ okHttp() }
        single { get<Retrofit>().create(Service::class.java) }
    }

    val viewModels = module {
        viewModel { SignUpViewModel() }
        viewModel { HomeViewModel(get()) }
    }

}
