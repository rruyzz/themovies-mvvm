package dominando.android.moviesdb.di

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.facebook.FacebookSdk
import dominando.android.moviesdb.login.signup.SignUpViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyCustomApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val appModules = listOf(viewModels)
        startKoin {
            modules(appModules)
            androidLogger()
            androidContext(this@MyCustomApp)
        }
    }

    val viewModels = module {
        viewModel { SignUpViewModel() }
    }
}