package view

import BottomNavigationDirection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import controller.ProfileController
import controller.RedirectionController
import model.CustomerSkeleton

/**
 * ProfileActivity represents the user profile screen.
 * It displays details about the user and provides options for navigation.
 * Author: Ahmed Nasif Shahriar (P2719390)
 */
class ProfileActivity : AppCompatActivity() {

    /**
     * Called when the activity is first created.
     * @param savedInstanceState: If the activity is being re-initialized after previously
     * being shut down, this Bundle contains the data it most recently supplied in
     * onSaveInstanceState(Bundle).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Get user details from the Profile Controller
        val user = ProfileController().getTheProfileDetails(this)

        // Set user details to corresponding TextViews
        findViewById<TextView>(R.id.profileName).text = user?.getCusFullName()
        findViewById<TextView>(R.id.profileEmail).text = user?.getCusEmail()
        findViewById<TextView>(R.id.profileUsername).text = user?.getCusUsername()
        findViewById<TextView>(R.id.profilePhoneNumber).text = user?.getCusPhoneNo().toString()

        // Initialize the send feedback button
        val sendfeedbackbtn : Button = findViewById(R.id.sendFeedbackButton)
        val logout : Button = findViewById(R.id.logoutButton)

        // Initialize the bottom navigation view
        val bottomnav: BottomNavigationView = findViewById(R.id.bottomnavprofile)

        // Set up controllers for navigation
        BottomNavigationDirection().selectBottomNavigationItem(bottomnav, R.id.navigation_profile)
        BottomNavigationDirection().callBottomNavigation(bottomnav, this)

        // Set up click listener for the feedback button
        sendfeedbackbtn.setOnClickListener {
            // Redirect to the SendFeedbackActivity
            RedirectionController().Redirect(this, SendFeedbackActivity::class.java)
        }

        logout.setOnClickListener {

            val alertDialogBuilder = AlertDialog.Builder(this)


            alertDialogBuilder.setTitle("Logout Confirmation")
            alertDialogBuilder.setMessage("Are you sure you want to log out?")

            alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
                // Clear user data from shared preferences
                val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.clear()
                editor.apply()

                // Clear the instance of CustomerSkeleton
                CustomerSkeleton.clearIntanceCustomer()

                RedirectionController().Redirect(this, CustomerLoginn::class.java)
            }

            alertDialogBuilder.setNegativeButton("No") { _, _ ->

            }

            val alertDialog: AlertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }


    }

    override fun onBackPressed() {
        RedirectionController().Redirect(this , HomePage::class.java)
    }
}
