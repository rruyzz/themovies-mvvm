package dominando.android.moviesdb.utils.extensions

import android.app.Activity
import android.content.Context
import android.view.WindowManager
import android.widget.Toast

fun Activity.showToast(context: Context, text: String){
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun Activity.disableTouch(isDisable: Boolean){
    if(isDisable){
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    } else {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}