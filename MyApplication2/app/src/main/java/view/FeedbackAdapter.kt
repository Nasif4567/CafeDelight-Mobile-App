package view

import android.app.AlertDialog
import DBhelper
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import controller.FeedbackController
import controller.ShopManageCotroller
import model.Feedback

/**
 * FeedbackAdapter
 *
 * Adapter class for RecyclerView to display a list of feedback items. Manages the view for each
 * item in the list, including handling user interactions.
 *
 * @property context The context of the activity or fragment using this adapter.
 * @property feedbackList The list of feedback items to be displayed in the RecyclerView.
 */
class FeedbackAdapter(
    private val context: Context,
    private val feedbackList: MutableList<Feedback>,
) : RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder>() {

    /**
     * onCreateViewHolder
     *
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added.
     * @param viewType The view type of the new View.
     * @return A new FeedbackViewHolder that holds the View for the feedback item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.feedback_item_layout, parent, false)
        return FeedbackViewHolder(view)
    }

    /**
     * onBindViewHolder
     *
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder that should be updated to represent the contents of the item
     * at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: FeedbackViewHolder, position: Int) {
        val feedback = feedbackList[position]

        // Bind data to the ViewHolder
        holder.customerNameTextView.text = "Customer Name: ${feedback.getCustomerName()}"
        holder.customerEmailTextView.text = "Customer Email: ${feedback.getCustomerEmail()}"
        holder.customerIdTextView.text = "Customer ID: ${feedback.getCustomerId()}"
        holder.ratingTextView.text = "Rating: ${feedback.getRating()}"
        holder.feedbackCommentTextView.text =
            "Feedback Comment: ${feedback.getComment()}"

        // Set click listener for delete icon
        holder.deleteIconImageView.setOnClickListener {
            showDeleteConfirmationDialog(position)
        }
    }

    /**
     * getItemCount
     *
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of feedback items.
     */
    override fun getItemCount(): Int {
        return feedbackList.size
    }

    /**
     * FeedbackViewHolder
     *
     * ViewHolder class to hold the views for a feedback item.
     *
     * @property itemView The view for a single feedback item in the RecyclerView.
     */
    class FeedbackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val customerNameTextView: TextView = itemView.findViewById(R.id.textViewCustomerName)
        val customerEmailTextView: TextView = itemView.findViewById(R.id.textViewCustomerEmail)
        val customerIdTextView: TextView = itemView.findViewById(R.id.textViewCustomerId)
        val ratingTextView: TextView = itemView.findViewById(R.id.textViewRating)
        val feedbackCommentTextView: TextView =
            itemView.findViewById(R.id.textViewFeedbackComment)
        val deleteIconImageView: ImageView = itemView.findViewById(R.id.imageViewDelete)
    }

    /**
     * showDeleteConfirmationDialog
     *
     * Method to show a delete confirmation dialog when the delete icon is clicked.
     *
     * @param position The position of the feedback item in the adapter.
     */
    private fun showDeleteConfirmationDialog(position: Int) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Delete Feedback")
        alertDialogBuilder.setMessage("Are you sure you want to delete this feedback?")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            val processMessage =
                FeedbackController.deleteFeedback(position, context, feedbackList)
            if (processMessage.equals("Feedback Deleted")) {
                // Remove from the list and update UI
                feedbackList.removeAt(position)
                notifyItemRemoved(position)
            }

            ShowToast.showShortToast(context, processMessage)
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}

