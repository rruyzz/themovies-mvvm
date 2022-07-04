package dominando.android.moviesdb.utils.extensions

import android.app.Activity
import android.content.Context
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(context: Context, text: String){
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun Fragment.disableTouch(isDisable: Boolean){
    requireActivity().disableTouch(isDisable)
}