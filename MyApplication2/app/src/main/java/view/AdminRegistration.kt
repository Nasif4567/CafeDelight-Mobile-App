package view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import controller.AuthController
import controller.RedirectionController

/**
 * AdminRegistration
 *
 * This activity allows administrators to register a new account. It collects the user's full name, email,
 * phone number, username, and password. It performs validation checks on the input data, such as email format,
 * phone number format, and password strength. If the registration is successful, the user is redirected to the
 * AdminLogin activity.
 */
class AdminRegistration : AppCompatActivity() {

    // UI elements
    private lateinit var edtAdminFullName: EditText
    private lateinit var edtAdminEmail: EditText
    private lateinit var edtAdminPhoneNumber: EditText
    private lateinit var edtAdminUsername: EditText
    private lateinit var edtAdminPassword: EditText
    private lateinit var btnAdminRegister: Button
    private lateinit var directLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_registartion)

        // Initialize UI elements
        edtAdminFullName = findViewById(R.id.edtAdminFullName)
        edtAdminEmail = findViewById(R.id.edtAdminEmail)
        edtAdminPhoneNumber = findViewById(R.id.edtAdminPhoneNumber)
        edtAdminUsername = findViewById(R.id.edtAdminUsername)
        edtAdminPassword = findViewById(R.id.edtAdminPassword)
        btnAdminRegister = findViewById(R.id.Adminbtnregister)
        directLogin = findViewById(R.id.directLogi)

        // Set click listener for the register button
        btnAdminRegister.setOnClickListener {
            try {
                // Retrieve values from UI elements
                val fullName = edtAdminFullName.text.toString()
                val email = edtAdminEmail.text.toString()
                val phoneNumber = edtAdminPhoneNumber.text.toString()
                val username = edtAdminUsername.text.toString()
                val password = edtAdminPassword.text.toString()

                // Check for empty fields
                if (fullName.isBlank() || email.isBlank() || phoneNumber.isBlank() || username.isBlank() || password.isBlank()) {
                    ShowToast.showShortToast(this , "All the fields are required")
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

                // Validate password strength (you can customize this based on your requirements)
                if (password.length < 8) {
                    ShowToast.showShortToast(this, "Password must be at least 8 characters long")
                    return@setOnClickListener
                }

                if (!password.matches(Regex("^(?=.*[A-Za-z])(?=.*\\d).+\$"))) {
                    ShowToast.showShortToast(this, "Password must contain a mixture of letters and numbers")
                    return@setOnClickListener
                }

                // Call the performAdminRegistration method and pass the values
                val processMessage = AuthController().performAdminRegistration(
                    this,
                    fullName,
                    email,
                    phoneNumber.toInt(),
                    username,
                    password
                )

                // If registration is successful, redirect to AdminLogin activity
                if (processMessage == "Registration Successfull") {
                    RedirectionController().Redirect(this, AdminLogin::class.java)
                }

                ShowToast.showShortToast(this , processMessage)
            } catch (e: Exception) {
                // Handle exceptions and show an error message
                e.printStackTrace()
                ShowToast.showShortToast(this, "An error occurred: ${e.message}")
            }
        }

        // Set click listener for the "Admin Login here please" text
        directLogin.setOnClickListener {
            RedirectionController().Redirect(this , AdminLogin::class.java)
        }
    }

    /**
     * onBackPressed
     *
     * Overrides the back button functionality. Redirects the user to the MainActivity.
     */
    override fun onBackPressed() {
        RedirectionController().Redirect(this , MainActivity::class.java)
    }
}

