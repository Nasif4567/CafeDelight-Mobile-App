package view

import DBhelper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import controller.FeedbackController
import model.Feedback

/**
 * RatingViewFragment
 *
 * This fragment is responsible for displaying a list of feedback items in a RecyclerView.
 * It utilizes the FeedbackController to retrieve feedback data and displays it using the
 * FeedbackAdapter. If there are no feedback items, a TextView is shown to inform the user
 * that there is no feedback available.
 */
class RatingViewFragment : Fragment() {

    /**
     * onCreateView
     *
     * Called to create the view hierarchy associated with the fragment.
     *
     * @param inflater: The LayoutInflater object that can be used to inflate any
     *                  views in the fragment.
     * @param container: If non-null, this is the parent view that the fragment's UI
     *                   should be attached to.
     * @param savedInstanceState: If non-null, this fragment is being re-constructed from
     *                             a previous saved state as given here.
     * @return The root view of the fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_rating_view, container, false)

        // Retrieve feedback data from the controller
        val feedbackList: MutableList<Feedback> = FeedbackController.getAllFeedback(requireContext())

        // Initialize RecyclerView and TextView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewFeedback)
        val noFeedbackView: TextView = view.findViewById(R.id.noFeedbackTextView)

        // Show or hide the TextView based on the presence of feedback items
        if (feedbackList.isEmpty()) {
            noFeedbackView.visibility = View.VISIBLE
        }

        // Set up the RecyclerView with the FeedbackAdapter
        val feedbackAdapter = FeedbackAdapter(requireContext(), feedbackList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = feedbackAdapter

        return view
    }
}


