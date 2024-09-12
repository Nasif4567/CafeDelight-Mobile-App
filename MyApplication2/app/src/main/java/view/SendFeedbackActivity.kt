package view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import controller.FeedbackController
import controller.RedirectionController
import model.Feedback

/**
 * SendFeedbackActivity
 *
 * This activity allows users to submit feedback, including a rating and optional comments.
 * The feedback is processed by the FeedbackController, and a Toast message is displayed to
 * inform the user about the result of the feedback submission.
 */
class SendFeedbackActivity : AppCompatActivity() {

    private lateinit var ratingBar: RatingBar
    private lateinit var commentEditText: EditText

    /**
     * onCreate
     *
     * Initializes the activity and sets up the necessary views.
     *
     * @param savedInstanceState: The saved state of the activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_feedback)

        // Initialize views
        ratingBar = findViewById(R.id.ratingBar)
        commentEditText = findViewById(R.id.commentEditText)

        val submitButton: Button = findViewById(R.id.submitfed)

        // Set an OnClickListener for the button
        submitButton.setOnClickListener {
            // Validate input and submit feedback
            if (validateInput()) {
                // Gets result from the controller
                val ProcessMessage =
                    FeedbackController.submitFeedback(this, ratingBar.rating, commentEditText.text.toString())
                ShowToast.showShortToast(this, ProcessMessage)
            }
        }
    }

    /**
     * onBackPressed
     *
     * Handles the back button press by redirecting the user to the ProfileActivity.
     */
    override fun onBackPressed() {
        RedirectionController().Redirect(this, ProfileActivity::class.java)
    }

    /**
     * validateInput
     *
     * Validates the user input before submitting feedback. Displays an error message
     * if the rating is zero and the comment is empty.
     *
     * @return True if input is valid, false otherwise.
     */
    private fun validateInput(): Boolean {
        if (ratingBar.rating == 0f) {
            // Display an error message if the rating is zero
            ShowToast.showShortToast(this, "Please provide a rating.")
            return false
        }

        if (commentEditText.text.toString().trim().isEmpty()) {
            // Display an error message if the comment is empty
            ShowToast.showShortToast(this, "Please enter a comment.")
            return false
        }

        return true
    }
}

