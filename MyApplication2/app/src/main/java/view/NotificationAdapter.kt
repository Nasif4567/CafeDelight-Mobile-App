import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import model.Notification
import view.ProductViewAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NotificationAdapter(notificationList: List<Notification>) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    private val notificationList: List<Notification>

    init {
        this.notificationList = notificationList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(com.example.myapplication.R.layout.notificationviewlayout, parent, false)
        return NotificationAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notificationItem: Notification = notificationList[position]
        holder.senderNameTextView.setText(notificationItem.title)
        holder.messagePreviewTextView.setText(notificationItem.content)
        val actualTimestamp = getActualTimestamp(notificationItem.timestamp)
        holder.timestampTextView.text = actualTimestamp
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var senderNameTextView: TextView
        var messagePreviewTextView: TextView
        var timestampTextView: TextView

        init {
            senderNameTextView = itemView.findViewById<TextView>(com.example.myapplication.R.id.nt)
            messagePreviewTextView = itemView.findViewById<TextView>(com.example.myapplication.R.id.np)
            timestampTextView = itemView.findViewById<TextView>(com.example.myapplication.R.id.timestamp)
        }
    }


    private fun getActualTimestamp(timestamp: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val parsedDate = dateFormat.parse(timestamp)

        // Calculate the time difference
        val currentTime = Calendar.getInstance().time
        val diffInMillis = currentTime.time - parsedDate.time
        val seconds = diffInMillis / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return when {
            days >= 1 -> "$days days ago"
            hours >= 1 -> "$hours hours ago"
            minutes >= 1 -> "$minutes minutes ago"
            else -> "Just now"
        }
    }

}
