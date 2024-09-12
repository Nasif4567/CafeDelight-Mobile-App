package com.example.myapplication

import androidx.test.platform.app.InstrumentationRegistry
import controller.AuthController
import org.junit.Test

import org.junit.Assert.*

class UnitTest {
    @Test
    fun testPerformCustomerLogin() {
        // Context of the app under test.
       // val context = ApplicationProvider.getApplicationContext<Context>()
        val context =  InstrumentationRegistry.getInstrumentation().targetContext

        // Create an instance of AuthController
        val authController = AuthController()

        // Test case 1: Valid login credentials
        val result1 = authController.peformCustomerlogin("Nasif!123", "Nasif!123456789", context)
        assertEquals("Login Successfull", result1)

        // Test case 2: Empty username
        val result2 = authController.peformCustomerlogin("", "validPassword", context)
        assertEquals("Please enter your credentials!", result2)

        // Test case 3: Empty password
        val result3 = authController.peformCustomerlogin("validUsername", "", context)
        assertEquals("Please enter your credentials!", result3)

        // Test case 4: Incorrect credentials
        val result4 = authController.peformCustomerlogin("invalidUsername", "invalidPassword", context)
        assertEquals("Sorry Couldn't login you, please check you credentials.", result4)

        // Test case 5: Exception during login
        val result5 = authController.peformCustomerlogin("exception", "exception", context)
        assertEquals("Error during login: null", result5)
    }
}