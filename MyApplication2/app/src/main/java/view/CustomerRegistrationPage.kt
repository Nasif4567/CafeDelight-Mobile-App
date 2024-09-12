package view

import DBhelper
import com.example.myapplication.MainActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.myapplication.R
import controller.AuthController
import controller.RedirectionController

/**
 * CustomerRegistrationPage
 *
 * This activity handles the customer registration process, allowing users to create a new account by providing necessary information.
 */
class CustomerRegistrationPage : AppCompatActivity() {

    // Database helper instance
    private val db = DBhelper(this)

    /**
     * onCreate
     *
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_registration_page)

        // Initialize UI elements
        val edtFullName: EditText = findViewById(R.id.edtFullName)
        val edtEmail: EditText = findViewById(R.id.edtEmail)
        val edtPhoneNumber: EditText = findViewById(R.id.edtPhoneNumber)
        val edtUsername: EditText = findViewById(R.id.edtUsername)
        val edtPassword: EditText = findViewById(R.id.edtPassword)
        val redictlogi: TextView = findViewById(R.id.directLogi)

        // Set click listener for "Already registered? Login here" text
        redictlogi.setOnClickListener {
            RedirectionController().Redirect(this, CustomerLoginn::class.java)
        }

        // Set click listener for the register button
        val btnRegister: Button = findViewById(R.id.btnregister)
        btnRegister.setOnClickListener {
            try {
                // Get text from EditText views
                val fullName: String = edtFullName.text.toString()
                val email: String = edtEmail.text.toString()
                val phoneNumber: String = edtPhoneNumber.text.toString()
                val username: String = edtUsername.text.toString()
                val password: String = edtPassword.text.toString()

                // Validate input fields
                if (fullName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    ShowToast.showShortToast(this, "All the fields are required")
                    return@setOnClickListener
                }

                // Validate email format
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    ShowToast.showShortToast(this, "Invalid email format")
                    return@setOnClickListener
                }

                // Validate phone number format
                if (phoneNumber.length != 10) {
                    ShowToast.showShortToast(this, "Invalid phone number format. It should be 10 digits.")
                    return@setOnClickListener
                }

                // Validate password strength
                if (!password.matches(Regex("^(?=.*[A-Za-z])(?=.*\\d).+\$"))) {
                    ShowToast.showShortToast(this, "Password must contain a mixture of letters and numbers")
                    return@setOnClickListener
                }

                // Call the performCustomerRegistration method and pass the values
                val processMessage = AuthController().performCustomerRegistration(
                    this,
                    fullName,
                    email,
                    phoneNumber.toInt(),
                    username,
                    password
                )

                // Check if registration was successful and redirect accordingly
                if (processMessage.equals("Registration Successfull")) {
                    RedirectionController().Redirect(this, CustomerLoginn::class.java)
                }

                ShowToast.showShortToast(this, processMessage)
            } catch (e: Exception) {
                // Handle exceptions and show an error message
                e.printStackTrace()
                ShowToast.showShortToast(this, "An error occurred: ${e.message}")
            }
        }
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
