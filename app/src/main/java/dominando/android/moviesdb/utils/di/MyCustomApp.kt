package dominando.android.moviesdb.utils.di

import android.app.Application
import dominando.android.moviesdb.home.HomeRepository
import dominando.android.moviesdb.home.HomeViewModel
import dominando.android.moviesdb.login.signup.SignUpViewModel
import dominando.android.moviesdb.utils.api.Interceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyCustomApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val appModules = listOf(viewModels, network, repositorys)
        startKoin {
            modules(appModules)
            androidLogger()
            androidContext(this@MyCustomApp)
        }
    }

    val network = module {
//        single{ retrofit() }
//        single{ okHttp() }
//        single { get<Retrofit>().creatce(Service::class.java) }
        factory { Interceptor() }
//        factory { provideOkHttpClient(get()) }
//        factory { provideForecastApi(get()) }
//        single { provideRetrofit(get()) }
//        factory { ResponseHandler() }
    }

    val repositorys = module{
        factory { HomeRepository(get(), get()) }
    }
    val viewModels = module {
        viewModel { SignUpViewModel() }
        viewModel { HomeViewModel(get()) }
    }

}
