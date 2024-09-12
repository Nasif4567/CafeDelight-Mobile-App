package controller

import DBhelper
import NotificationManager
import android.content.Context
import android.widget.Toast
import model.Customers
import model.Order
import model.OrderDetails

/**
 * The `OrderManagerController` class provides methods for managing orders, such as retrieving, confirming, canceling, marking as done,
 * and deleting orders.
 */
class OrderManagerController {

    companion object {

        /**
         * Retrieves all orders from the database.
         *
         * @param context The context of the application.
         * @return A list of [Order] objects representing all orders.
         */
        fun getAllOrder(context: Context): List<Order> {
            return DBhelper(context).getAllOrders()
        }

        /**
         * Retrieves all order details for a specific order ID from the database.
         *
         * @param context The context of the application.
         * @param orderid The ID of the order for which to retrieve order details.
         * @return A list of [OrderDetails] objects representing order details for the specified order.
         */
        fun getAllOrderDetailsByOrderID(context: Context, orderid: String): List<OrderDetails> {
            return DBhelper(context).getAllOrderDetailsByOrderID(orderid)
        }

        /**
         * Sets the order status to "Confirmed" and sends a confirmation notification.
         *
         * @param item The [Order] object representing the order to be confirmed.
         * @param contextt The context of the application.
         * @return A list of [Order] objects representing all orders after the confirmation.
         */
        fun setOrderConfirm(item: Order, contextt: Context): List<Order> {
            try {
                val orderId = item.getOrderId()
                val cusID = item.getOrderCusId()
                val cus: List<Customers> = DBhelper(contextt).getAllCustomers()
                var cusname: String? = null
                val customer = cus.find { it.getCusid() == cusID }
                cusname = customer?.getCusFullName()

                if (orderId != null) {
                    DBhelper(contextt).updateOrderStatus(orderId, "Confirmed")
                    NotificationManager(contextt).showNotification("Hey $cusname", "Order is Confirmed! Now Being Prepared")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return DBhelper(contextt).getAllOrders()
        }

        /**
         * Sets the order status to "Cancelled" and sends a cancellation notification.
         *
         * @param item The [Order] object representing the order to be cancelled.
         * @param contextt The context of the application.
         * @return A list of [Order] objects representing all orders after the cancellation.
         */
        fun setOrderCancel(item: Order, contextt: Context): List<Order> {
            try {
                val orderId = item.getOrderId()
                val cusID = item.getOrderCusId()
                val cus: List<Customers> = DBhelper(contextt).getAllCustomers()
                var cusname: String? = null
                val customer = cus.find { it.getCusid() == cusID }
                cusname = customer?.getCusFullName()

                if (orderId != null) {
                    DBhelper(contextt).updateOrderStatus(orderId, "Cancelled")
                    NotificationManager(contextt).showNotification("Hey $cusname", "Sorry to say but your order has been cancelled.")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return DBhelper(contextt).getAllOrders()
        }

        /**
         * Sets the order status to "Done" and sends a notification to collect the order.
         *
         * @param item The [Order] object representing the order to be marked as done.
         * @param contextt The context of the application.
         * @return A list of [Order] objects representing all orders after marking as done.
         */
        fun setOrderDone(item: Order, contextt: Context): List<Order> {
            try {
                val orderId = item.getOrderId()
                val cusID = item.getOrderCusId()
                val cus: List<Customers> = DBhelper(contextt).getAllCustomers()
                var cusname: String? = null
                val customer = cus.find { it.getCusid() == cusID }
                cusname = customer?.getCusFullName()

                if (orderId != null) {
                    DBhelper(contextt).updateOrderStatus(orderId, "Done")
                    NotificationManager(contextt).showNotification("Hey $cusname", "Order is Done, Collect Please!")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return DBhelper(contextt).getAllOrders()
        }

        /**
         * Deletes an order based on the order status and returns a message indicating the result.
         *
         * @param item The [Order] object representing the order to be deleted.
         * @param contextt The context of the application.
         * @return A string message indicating the result of the order deletion.
         */
        fun deleteorder(item: Order, contextt: Context): String {
            var processMessage: String = ""
            try {
                val orderId = item.getOrderId()
                val orderStatus = item.getOrderStatus()

                if (orderId != null) {
                    // Check if the order status is "Done" or "Cancelled"
                    if (orderStatus == "Done" || orderStatus == "Cancelled") {
                        // Delete the order
                        val rowsAffected = DBhelper(contextt).deleteOrder(orderId.toString())

                        if (rowsAffected > 0) {
                            return "Order deleted successfully"
                        } else {
                            processMessage = "Failed to delete order"
                        }
                    } else {
                        // Order is in progress, show a toast
                        processMessage =
                            "Cannot delete order. Order is in progress. Please make the order done or cancel the order to delete it."
                    }
                }
            } catch (e: Exception) {
                // Handle the exception here
                e.printStackTrace()
                processMessage = "Error in deleting order"
            }

            return processMessage
        }
    }
}
