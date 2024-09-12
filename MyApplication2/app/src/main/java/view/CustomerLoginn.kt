package view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import DBhelper
import android.widget.TextView
import com.example.myapplication.MainActivity
import controller.AuthController
import controller.RedirectionController

/**
 * CustomerLoginn
 *
 * This activity handles the login functionality for customers, allowing them to enter their username and password to log in.
 */
class CustomerLoginn : AppCompatActivity() {

    // UI elements
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var redirectRegistration: TextView
    private lateinit var dbHelper: DBhelper
    private lateinit var sharedPreferences: SharedPreferences

    /**
     * onCreate
     *
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_loginn)

        // Initialize UI elements
        usernameEditText = findViewById(R.id.customerusernameedt)
        passwordEditText = findViewById(R.id.customerpasswordedt)
        loginButton = findViewById(R.id.cusoginbutton)
        redirectRegistration = findViewById(R.id.directRegi)
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Set click listener for the login button
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val processMessage = AuthController().performCustomerLogin(username, password, this)
            if (processMessage.equals("Login Successfull")) {
                RedirectionController().Redirect(this, HomePage::class.java)
                saveUsername(username)
            }

            ShowToast.showShortToast(this, processMessage)
        }

        // Set click listener for the "Not registered? Register here" text
        redirectRegistration.setOnClickListener {
            try {
                RedirectionController().Redirect(this, CustomerRegistrationPage::class.java)
            } catch (e: Exception) {
                ShowToast.showShortToast(this, e.message.toString())
            }
        }
    }

    /**
     * saveUsername
     *
     * Save the username in SharedPreferences.
     *
     * @param username The username to be saved.
     */
    private fun saveUsername(username: String) {
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.apply()
    }

    /**
     * onBackPressed
     *
     * Called when the activity has detected the user's press of the back key.
     */
    override fun onBackPressed() {
        RedirectionController().Redirect(this, MainActivity::class.java)
    }
}
