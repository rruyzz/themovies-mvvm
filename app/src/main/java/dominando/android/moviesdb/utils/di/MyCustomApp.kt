package dominando.android.moviesdb.utils.di

import android.app.Application
import dominando.android.moviesdb.home.HomeViewModel
import dominando.android.moviesdb.login.signup.SignUpViewModel
import dominando.android.moviesdb.movieDetail.MovieDetailViewModel
import dominando.android.moviesdb.search.SearchViewModel
import dominando.android.moviesdb.search.seriesSearch.SeriesSearchViewModel
import dominando.android.moviesdb.serieDetail.SerieDetailViewModel
import dominando.android.moviesdb.utils.api.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit

class MyCustomApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val appModules = listOf( retrofitModule, viewModels, apiModule)
        startKoin {
            androidLogger()
            androidContext(this@MyCustomApp)
            modules(appModules)
        }
    }
    val apiModule = module {
        single(createdAtStart = false) { get<Retrofit>().create(Service::class.java) }
    }
    val retrofitModule = module{
        single { createHttpClient() }
        single { retrofitClient(get())}
    }
    val viewModels = module {
        viewModel { SignUpViewModel() }
        viewModel { HomeViewModel(get()) }
        viewModel { MovieDetailViewModel(get()) }
        viewModel { SerieDetailViewModel(get()) }
        viewModel { SearchViewModel(get()) }
        viewModel { SeriesSearchViewModel(get()) }
    }
}
