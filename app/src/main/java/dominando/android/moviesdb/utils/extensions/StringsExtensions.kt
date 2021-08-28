package dominando.android.moviesdb.utils.extensions

import android.util.Log
import java.lang.Exception

fun String.formattedAsHour() : String {
    try {
        val hours = this.toInt() / 60
        val minutesRest = this.toInt() % 60
        val minutes = if(minutesRest>9) minutesRest.toString()
            else "0$minutesRest"
        return "${hours}h${minutes}"
    } catch (e: Exception) {
        Log.e("MoviesDetail", "exception", e);
        return ""
    }
}

fun String.formtattedAsDate() = "${this.substring(8, length)}/${this.substring(5,7)}/${this.substring(0, 4)}"
