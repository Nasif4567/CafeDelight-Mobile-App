package view

import BottomNavigationDirection
import NotificationAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import model.Notification
import DBhelper
import android.view.View
import android.widget.TextView
import controller.CustomerNotificationController
import controller.RedirectionController
import model.Order


/**
 * NotificationView
 *
 * Activity to display customer notifications using a RecyclerView. It retrieves order information
 * and converts it into notification items for display.
 */
class NotificationView : AppCompatActivity() {

    private lateinit var db: DBhelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_view)

        db = DBhelper(this)

        val noNotification: TextView = findViewById(R.id.noNotificationsTextView)
        val bottomNav: BottomNavigationView = findViewById(R.id.bottomnavnoti)

        // Select and set up the bottom navigation item for notifications
        BottomNavigationDirection().selectBottomNavigationItem(bottomNav, R.id.navigation_notification)
        BottomNavigationDirection().callBottomNavigation(bottomNav, this)

        // Initialize RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.notificationRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Get the list of orders for the customer
        val orderList: List<Order> = CustomerNotificationController().getAllNotificationBasedonCustomer(this)

        // Create a list to hold Notification objects
        val notificationList: MutableList<Notification> = mutableListOf()

        // Show a message if there are no notifications
        if (orderList.isEmpty()) {
            noNotification.visibility = View.VISIBLE
        }

        // Convert each order into a notification item
        for (order in orderList) {
            val notification: Notification = Notification(
                "Your Order ID : ${order.getOrderId()}",
                "Your Order Progress : ${order.getOrderStatus()}",
                "${order.getOrderDate()} ${order.getOrderTime()}",
            )
            notificationList.add(notification)
        }

        // Create and set the adapter for the RecyclerView
        val adapter = NotificationAdapter(notificationList)
        recyclerView.adapter = adapter
    }

    override fun onBackPressed() {
        // Redirect to the home page when the back button is pressed
        RedirectionController().Redirect(this, HomePage::class.java)
    }
}

