package dominando.android.moviesdb.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dominando.android.moviesdb.R
import dominando.android.moviesdb.login.signup.SignUpViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
//        val appModules = listOf(viewModels)
//        startKoin {
//            modules(appModules)
//            androidLogger()
//            androidContext(this@SignUpActivity)
//        }
//    }
//
//    val viewModels = module {
//        viewModel { SignUpViewModel() }
    }
}