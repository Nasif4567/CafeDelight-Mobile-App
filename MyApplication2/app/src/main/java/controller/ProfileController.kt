package controller

import model.CustomerSkeleton
import DBhelper
import android.content.Context
import model.Customers

/**
 * The `ProfileController` class provides methods for retrieving profile details of a customer.
 */
class ProfileController {

    /**
     * Retrieves the profile details of the currently logged-in customer.
     *
     * @param context The context of the application.
     * @return A [Customers] object representing the profile details of the customer.
     *         Returns null if the customer is not found.
     */
    fun getTheProfileDetails(context: Context): Customers? {
        // Retrieve the username of the currently logged-in customer from CustomerSkeleton
        val username = CustomerSkeleton.getInstanceCustomer().getCusUsername()

        // Retrieve and return the customer details based on the username
        return DBhelper(context).getCustomerByUsername(username)
    }
}

