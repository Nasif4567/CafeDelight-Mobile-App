package view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import controller.OrderManagerController
import controller.RedirectionController
import model.OrderDetails

class OrderDetailsView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details_view)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewodetails)

        // Get the order ID from the intent
        val orderid = intent.getIntExtra("orderI", -1) // Assuming the order ID is an integer
        Toast.makeText(this, "Order ID: $orderid", Toast.LENGTH_SHORT).show()

        if (orderid != -1) {
            // Fetch order details for the specific order ID
            val orderlist: List<OrderDetails> = OrderManagerController.getAllOrderDetailsByOrderID(this , orderid.toString())

            val layoutManager = LinearLayoutManager(this)
            recyclerView.layoutManager = layoutManager
            val adapter = OrderDetailsItemAdapter(orderlist, this)
            recyclerView.adapter = adapter
        } else {
            // Handle the case when order ID is not provided
            Toast.makeText(this, "Invalid Order ID", Toast.LENGTH_SHORT).show()
            // You might want to finish the activity or redirect the user to a proper screen.
        }
    }


}