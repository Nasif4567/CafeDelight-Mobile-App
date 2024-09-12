package controller

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity

/**
 * The `RedirectionController` class provides a method for redirecting to a specified activity class.
 */
class RedirectionController {

    /**
     * Redirects to the specified activity class.
     *
     * @param context The context of the application.
     * @param redirect The target activity class to which the redirection should occur.
     */
    fun Redirect(context: Context, redirect: Class<*>) {
        // Create an intent to start the specified activity
        val intent = Intent(context, redirect)

        // Start the activity
        context.startActivity(intent)
    }
}