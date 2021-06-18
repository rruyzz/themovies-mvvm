package dominando.android.moviesdb.di

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyCustomApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            modules(firebaseModule)
//            androidLogger(Level.DEBUG)
//            androidContext(this@MyCustomApp)
            }
    }

    val firebaseModule = module{
        single { FirebaseAuth.getInstance() }
    }
}