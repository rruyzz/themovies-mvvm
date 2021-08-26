package dominando.android.moviesdb.utils.extensions

import android.app.Activity
import android.content.Context
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
import android.widget.Toast

fun Activity.showToast(context: Context, text: String){
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun Activity.disableTouch(isDisable: Boolean){
    if(isDisable){
        window.setFlags(FLAG_NOT_TOUCHABLE, FLAG_NOT_TOUCHABLE)
        window.setFlags(FLAG_NOT_TOUCHABLE, FLAG_NOT_TOUCHABLE)
    } else {
        window.clearFlags(FLAG_NOT_TOUCHABLE)
        window.clearFlags(FLAG_NOT_TOUCHABLE)
    }
}