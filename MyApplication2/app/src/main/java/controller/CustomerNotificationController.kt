package controller

import DBhelper
import android.content.Context
import model.CustomerSkeleton
import model.Order

/**
 * The `CustomerNotificationController` class provides methods for retrieving notifications related to a customer.
 *
 * @constructor Creates an instance of [CustomerNotificationController].
 */
class CustomerNotificationController {

    /**
     * Retrieves all notifications based on the customer and returns a list of orders.
     *
     * @param context The context of the application.
     * @return A list of [Order] objects representing notifications for the customer.
     */
    fun getAllNotificationBasedonCustomer(context: Context): List<Order> {
        // Retrieve customer ID based on the username using CustomerSkeleton
        val customerId = DBhelper(context).getCustomerIdByUsername(CustomerSkeleton.getInstanceCustomer().getCusUsername())

        // Retrieve all orders for the customer using the customer ID
        val orderList: List<Order> = DBhelper(context).getAllOrdersByCustomerId(customerId)

        return orderList
    }
}
