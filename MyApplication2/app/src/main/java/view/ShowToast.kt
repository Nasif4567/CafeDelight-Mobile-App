package view

import android.content.Context
import android.widget.Toast

object ShowToast {

    /**
     * Show a short toast message.
     *
     * @param context The context in which the toast will be displayed.
     * @param message The message to be displayed in the toast.
     */
    fun showShortToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Show a long toast message.
     *
     * @param context The context in which the toast will be displayed.
     * @param message The message to be displayed in the toast.
     */
    fun showLongToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
