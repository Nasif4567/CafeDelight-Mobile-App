package view


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import controller.AuthController
import controller.RedirectionController
import model.AdminSkeleton


/**
 * AdminLogin
 *
 * This activity handles the login functionality for the admin. It allows the admin to input their
 * username and password to log in. If the login is successful, the admin is redirected to the AdminPanel.
 * Additionally, there is an option to navigate to the AdminRegistration page for new admin registrations.
 */
class AdminLogin : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var adminLoginButton: Button
    private lateinit var redirectRegistration: TextView
    private lateinit var sharedPreferences: SharedPreferences

    /**
     * onCreate
     *
     * Called to do initial creation of the activity. This is where you should do all of your normal
     * static set up: create views, bind data to lists, etc.
     *
     * @param savedInstanceState: If the activity is being re-initialized after previously being shut down
     *                           then this Bundle contains the data it most recently supplied in
     *                           onSaveInstanceState(Bundle). Note: Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        // Initialize UI elements
        usernameEditText = findViewById(R.id.adminusernamelogin)
        passwordEditText = findViewById(R.id.adminpasswordlogin)
        adminLoginButton = findViewById(R.id.adminloginbtn)
        redirectRegistration = findViewById(R.id.directAdminRegistration)
        sharedPreferences = getSharedPreferences("AdminMyPrefs", Context.MODE_PRIVATE)

        // Set click listener for the login button
        adminLoginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Perform admin login and handle the result
            var processMessage = AuthController().performAdminLogin(username, password, this)

            // If login is successful, save admin username and redirect to AdminPanel
            if (processMessage == "Login Successfull") {
                saveAdminUsername(username)
                RedirectionController().Redirect(this, AdminPanel::class.java)
            }

            ShowToast.showShortToast(this, processMessage)
        }

        // Set click listener for navigating to AdminRegistration page
        redirectRegistration.setOnClickListener {
            try {
                RedirectionController().Redirect(this, AdminRegistration::class.java)
            } catch (e: Exception) {
                ShowToast.showShortToast(this, e.message.toString())
            }
        }
    }

    /**
     * saveAdminUsername
     *
     * Saves the admin username in SharedPreferences for future use.
     *
     * @param username: The admin's username to be saved.
     */
    private fun saveAdminUsername(username: String) {
        // Save username in SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.apply()
    }

    /**
     * onBackPressed
     *
     * Overrides the default behavior of the back button to redirect to the MainActivity.
     */
    override fun onBackPressed() {
        RedirectionController().Redirect(this, MainActivity::class.java)
    }
}

