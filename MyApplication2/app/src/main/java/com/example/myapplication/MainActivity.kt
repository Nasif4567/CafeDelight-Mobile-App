package com.example.myapplication
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import controller.RedirectionController
import model.CustomerSkeleton
import view.AdminLogin
import view.AdminPanel
import view.HomePage
import view.CustomerLoginn



class MainActivity : AppCompatActivity() {

    // Flag to track whether to exit the application or not
    private var shouldExitApp = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn1: Button = findViewById(R.id.button2)
        val btn2: Button = findViewById(R.id.button3)

        // Load shared preference
        val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val savedUsername = sharedPreferences.getString("username", "")

        btn1.setOnClickListener {
            RedirectionController().Redirect(this, AdminLogin::class.java)
        }

        btn2.setOnClickListener {
            try {
                val customerInstance = CustomerSkeleton.getInstanceCustomer()

                // Set the loaded username to the customer instance
                if (savedUsername != null) {
                    customerInstance.setCusUsername(savedUsername)
                }

                if (customerInstance.getCusUsername().isNullOrEmpty()) {
                    shouldExitApp = false // Reset the flag when navigating to other activities
                    RedirectionController().Redirect(this, CustomerLoginn::class.java)
                } else {
                    RedirectionController().Redirect(this, HomePage::class.java)
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        if (shouldExitApp) {
            finishAffinity()
        } else {

        }
    }

    override fun onResume() {
        super.onResume()
        shouldExitApp = true
    }
}
