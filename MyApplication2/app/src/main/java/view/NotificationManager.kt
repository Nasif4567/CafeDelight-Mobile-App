import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.myapplication.R

/**
 * NotificationManager
 *
 * A utility class responsible for managing and displaying notifications.
 *
 * @property context The context in which the NotificationManager is created.
 */
class NotificationManager(private val context: Context) {

    // Lazily initialize the system NotificationManager
    private val notificationManager: NotificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    // Notification channel ID
    private val channelId = "OrderStatusChannel"

    /**
     * Initialization block that creates the notification channel on the first instantiation of the class.
     */
    init {
        createNotificationChannel()
    }

    /**
     * showNotification
     *
     * Displays a custom notification with the provided title and content.
     *
     * @param title The title of the notification.
     * @param content The content of the notification.
     */
    fun showNotification(title: String, content: String) {
        // Create a custom notification layout using RemoteViews
        val remoteViews = RemoteViews(context.packageName, R.layout.notificationbar_layout)

        // Set the title and content dynamically
        remoteViews.setTextViewText(R.id.notification_title, title)
        remoteViews.setTextViewText(R.id.notification_content, content)

        // Create the notification using NotificationCompat.Builder
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_baseline_coffee_24)
            .setCustomContentView(remoteViews)
            .setAutoCancel(true)

        // Show the notification using the system NotificationManager
        notificationManager.notify(1, builder.build())
    }

    /**
     * createNotificationChannel
     *
     * Creates a notification channel for Android versions Oreo (26) and above.
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Order Status",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            // Register the notification channel with the system NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
