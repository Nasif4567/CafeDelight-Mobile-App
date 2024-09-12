package controller

import DBhelper
import android.content.Context
import android.util.Log
import model.Admin
import model.AdminSkeleton
import model.CustomerSkeleton
import model.Customers

/**
 * The `AuthController` class contains methods for handling authentication operations such as
 * customer and admin login, as well as customer and admin registration.
 *
 * @constructor Creates an instance of [AuthController].
 */
/**
 * The `AuthController` class contains methods for handling authentication operations such as
 * customer and admin login, as well as customer and admin registration.
 *
 * @constructor Creates an instance of [AuthController].
 */
class AuthController {

    /**
     * Handles customer login authentication.
     *
     * @param username The customer's username.
     * @param password The customer's password.
     * @param context The context of the application.
     * @return A string indicating the login status (success or failure).
     */
    fun performCustomerLogin(username: String, password: String, context: Context): String {
        try {
            // Validate inputs
            if (username.isEmpty() || password.isEmpty()) {
                // Show an error message if username or password is empty
                return "Please enter your credentials!"
            }

            // Set the username and password in CustomerSkeleton
            CustomerSkeleton.getInstanceCustomer().setCusUsername(username)
            CustomerSkeleton.getInstanceCustomer().setCusPassword(password)

            // Verify customer login using DBHelper
            if (DBhelper(context).verifyLogin(username, password)) {
                // Authentication successful
                return "Login Successfull"
            } else {
                // Authentication failed
                return "Sorry, couldn't log you in. Please check your credentials."
            }
        } catch (e: Exception) {
            // Handle exceptions and show an error message
            e.printStackTrace()
            return "Error during login: ${e.message}"
        }
    }

    /**
     * Handles customer registration.
     *
     * @param context The context of the application.
     * @param fullName The customer's full name.
     * @param email The customer's email address.
     * @param phoneNumber The customer's phone number (optional).
     * @param username The desired username for registration.
     * @param password The desired password for registration.
     * @return A string indicating the registration status (success or failure).
     */
    fun performCustomerRegistration(
        context: Context,
        fullName: String,
        email: String,
        phoneNumber: Int?,
        username: String,
        password: String
    ): String {
        try {
            val dbHelper = DBhelper(context)

            // Check if the username is unique
            if (dbHelper.isCustomerUnique(username, email, phoneNumber).equals("OK")) {
                // Create a Customers object
                val customers =
                    Customers(fullName, email, phoneNumber, username, password, true)

                // Insert customer into the database
                dbHelper.insertCustomer(customers)

                return "Registration Successfull"
            } else {
                val alreadyTaken = dbHelper.isCustomerUnique(username, email, phoneNumber)
                return alreadyTaken
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return "Sorry, an error occurred during registration. ${e.message}"
        }
    }

    /**
     * Handles admin login authentication.
     *
     * @param username The admin's username.
     * @param password The admin's password.
     * @param context The context of the application.
     * @return A string indicating the login status (success or failure).
     */
    fun performAdminLogin(username: String, password: String, context: Context): String {
        try {
            // Validate inputs
            if (username.isEmpty() || password.isEmpty()) {
                // Show an error message if username or password is empty
                return "Please enter your credentials!"
            }

            // Set the username and password in AdminSkeleton
            AdminSkeleton.getInstanceAdmin().setAdminUsername(username)
            AdminSkeleton.getInstanceAdmin().setAdminPassword(password)

            // Verify admin login using DBHelper
            if (DBhelper(context).verifyAdminLogin(username, password)) {
                // Authentication successful
                return "Login Successfull"
            } else {
                // Authentication failed
                return "Sorry, couldn't log you in. Please check your credentials."
            }
        } catch (e: Exception) {
            // Handle exceptions and show an error message
            e.printStackTrace()
            return "Error during login: ${e.message}"
        }
    }

    /**
     * Handles admin registration.
     *
     * @param context The context of the application.
     * @param fullName The admin's full name.
     * @param email The admin's email address.
     * @param phoneNumber The admin's phone number (optional).
     * @param username The desired username for registration.
     * @param password The desired password for registration.
     * @return A string indicating the registration status (success or failure).
     */
    fun performAdminRegistration(
        context: Context,
        fullName: String,
        email: String,
        phoneNumber: Int?,
        username: String,
        password: String
    ): String {
        try {
            val dbHelper = DBhelper(context)

            // Check if the username is unique
            if (dbHelper.isAdminUnique(username, email, phoneNumber).equals("OK")) {
                // Create an Admin object
                val admin = Admin(fullName, email, phoneNumber, username, password, true)

                // Insert admin into the database
                dbHelper.insertAdmin(admin)

                return "Registration Successfull"
            } else {
                val alreadyTaken = dbHelper.isAdminUnique(username, email, phoneNumber)
                return alreadyTaken
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return "Sorry, an error occurred during registration. ${e.message}"
        }
    }
}
