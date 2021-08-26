package dominando.android.moviesdb.utils.extensions

import android.app.Activity
import android.content.Context
import android.widget.Toast

fun Activity.showToast(context: Context, text: String){
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}