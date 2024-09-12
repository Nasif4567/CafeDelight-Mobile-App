package com.example.myapplication

import DBhelper
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import controller.AuthController
import model.Customers
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class DBHelperTest {

    private lateinit var dbHelper: DBhelper

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dbHelper = DBhelper(context)
    }

    @Test
    fun testInsertCustomer() {
        // Create a sample Customers object
        val customers = Customers("John Doe","john.doe@example.com",1234567890, "john_doe", "password123" , true)

        // Insert the customer and get the result
        val result = dbHelper.insertCustomer(customers)

        // Check if the result is greater than 0 (indicating successful insertion)
        assertEquals(true, result > 0)
    }
}
