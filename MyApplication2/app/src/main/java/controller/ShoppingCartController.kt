package controller

import DBhelper
import android.content.Context
import model.CartItem
import model.CustomerSkeleton
import model.Order
import model.OrderDetails
import model.Product
import model.ShoppingCartSingleton
import view.HomePage
import java.util.Calendar

/**
 * ShoppingCartController
 *
 * This controller class handles interactions related to the shopping cart functionality,
 * including retrieving cart items, performing checkout, and managing date and time information.
 */
class ShoppingCartController {

    companion object {

        /**
         * getShoppingCartItems
         *
         * Retrieves the list of products in the shopping cart.
         *
         * @return List of products in the shopping cart.
         */
        fun getShoppingCartItems(): MutableList<Product> {
            val cartproductlist: MutableList<Product> =
                ShoppingCartSingleton.getShoppingCartInstance().getProducts()
            return cartproductlist
        }

        /**
         * checkout
         *
         * Handles the checkout process by inserting order details into the database.
         *
         * @param context: The context of the calling activity.
         * @return A message indicating the success or failure of the checkout process.
         */
        fun checkout(context: Context): String {
            var Processmessage: String = ""
            try {
                val db: DBhelper
                db = DBhelper(context)

                // Get the customer ID
                val cusid: Int =
                    db.getCustomerIdByUsername(CustomerSkeleton.getInstanceCustomer().getCusUsername())

                // Insert Into order database and get the order ID
                val orderid: Long = db.insertOrder(
                    Order(
                        cusid,
                        getTodaysDate(),
                        getCurrentTime(),
                        "On Progress"
                    )
                )

                // gets all the shopping cart items
                val shoppingcartproduct: List<CartItem> =
                    ShoppingCartSingleton.getShoppingCartInstance().getItems()

                // By iterating through the shopping cart, insert into the order details model
                shoppingcartproduct.forEach {
                    db.insertOrderDetails(OrderDetails(orderid.toInt(), it.product.getProid(), it.quantity))
                }

                Processmessage = "Checkout Successful"
                ShoppingCartSingleton.clearShoppingCartInstance()
                RedirectionController().Redirect(context, HomePage::class.java)

            } catch (e: Exception) {
                // Handle the exception and show an error Toast
                e.printStackTrace()
                Processmessage = e.message.toString()
            }

            return Processmessage
        }

        /**
         * getTodaysDate
         *
         * Retrieves the current date in the "yyyy-MM-dd" format.
         *
         * @return Current date in the "yyyy-MM-dd" format.
         */
        fun getTodaysDate(): String {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            return "$year-$month-$day"
        }

        /**
         * getCurrentTime
         *
         * Retrieves the current time in the "HH:mm:ss" format.
         *
         * @return Current time in the "HH:mm:ss" format.
         */
        fun getCurrentTime(): String {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            val second = calendar.get(Calendar.SECOND)

            return "$hour:$minute:$second"
        }

    }

}
