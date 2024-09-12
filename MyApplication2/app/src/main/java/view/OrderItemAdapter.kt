package view

import DBhelper
import NotificationManager
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import controller.OrderManagerController
import model.Customers
import model.Order

/**
 * RecyclerView Adapter for displaying a list of orders.
 *
 * @param OrderList List of orders to be displayed.
 * @param contextt Context of the activity or fragment using the adapter.
 */
class OrderItemAdapter(private var OrderList: List<Order>, private val contextt: Context) : RecyclerView.Adapter<OrderItemAdapter.ViewHolder>() {

    /**
     * ViewHolder class to hold references to the UI elements of each item view.
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val oid: TextView = itemView.findViewById(R.id.orderidd)
        val ocusid: TextView = itemView.findViewById(R.id.palacedbycusid)
        val odate: TextView = itemView.findViewById(R.id.orderDate)
        val ostat: TextView = itemView.findViewById(R.id.orderTime)
        val odbtnr: TextView = itemView.findViewById(R.id.viewOrderItembtn)
        val oprogress: ImageView = itemView.findViewById(R.id.progressbtn)
        val odone: ImageView = itemView.findViewById(R.id.donebtn)
        val ocancel: ImageView = itemView.findViewById(R.id.cancelbtn)
        val ostatus: TextView = itemView.findViewById(R.id.orderStatusview)
        val deleteorder : ImageView = itemView.findViewById(R.id.deleteorderbtn)
    }

    /**
     * Creates a new ViewHolder by inflating the layout for each item view.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemAdapter.ViewHolder {
        val view = try {
            LayoutInflater.from(parent.context).inflate(R.layout.orderitemslayout, parent, false)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(contextt, "Error inflating view " + e.message, Toast.LENGTH_SHORT).show()
            View(contextt)
        }
        return OrderItemAdapter.ViewHolder(view)
    }

    /**
     * Returns the total number of items in the order list.
     */
    override fun getItemCount(): Int {
        return OrderList.size
    }

    /**
     * Binds data to the views of each item view and sets click listeners.
     */
    override fun onBindViewHolder(holder: OrderItemAdapter.ViewHolder, position: Int) {
        val item = OrderList[position]

        // Binding data to TextViews and ImageViews
        holder.oid.text = "Order ID : ${item.getOrderId()}"
        holder.ocusid.text = "Placed by Customer ID: ${item.getOrderCusId()}"
        holder.odate.text = "Order Date: ${item.getOrderDate()}"
        holder.ostat.text = "Order Time: ${item.getOrderTime()}"
        holder.ostatus.text = "Order Status: ${item.getOrderStatus()}"

        // This on click listener updates view the order details
        holder.odbtnr.setOnClickListener {
            try {
                val intent = Intent(contextt, OrderDetailsView::class.java)
                val orderId = item.getOrderId()
                // Pass the order ID as an integer, not the TextView
                intent.putExtra("orderI", orderId)
                contextt.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(contextt, "Error handling click event " + e.message, Toast.LENGTH_SHORT).show()
            }
        }

        // This on click listener changes progress to confirm
        holder.oprogress.setOnClickListener {
            val orderlist = OrderManagerController.setOrderConfirm(item , contextt)
            updateAdapter(orderlist)
        }

        // This on click listener changes progress to done
        holder.odone.setOnClickListener {
            val orderlist = OrderManagerController.setOrderDone(item , contextt)
            updateAdapter(orderlist)
        }

        // This on click listener changes progress to cancelled
        holder.ocancel.setOnClickListener {
            // Handle the click event
            val orderlist = OrderManagerController.setOrderCancel(item , contextt)
            updateAdapter(orderlist)
        }

        //---------------------------------------------------------------

        // This click listener for the to delete the order
        holder.deleteorder.setOnClickListener {
            // Display a confirmation dialog using MaterialAlertDialogBuilder
            val alertDialogBuilder = MaterialAlertDialogBuilder(contextt)
                .setTitle("Confirmation")
                .setMessage("Are you sure you want to delete this order?")
                .setPositiveButton("Yes") { _, _ ->
                    // User clicked Yes, proceed with the deletion
                    val processMessage = OrderManagerController.deleteorder(item, contextt)

                    if (processMessage == "Order deleted successfully") {
                        updateAdapter(DBhelper(contextt).getAllOrders())
                    }

                    ShowToast.showShortToast(contextt, processMessage)
                }
                .setNegativeButton("No") { dialog, _ ->
                    // User clicked No, do nothing
                    dialog.dismiss()
                }

            alertDialogBuilder.show()
        }

//---------------------------------------------------------------------------
    }

    /**
     * Updates the adapter with the new order list.
     *
     * @param orderList The updated list of orders.
     */
    private fun updateAdapter(orderList: List<Order>) {
        OrderList = orderList
        notifyDataSetChanged()
    }
}

