package view

import DBhelper
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import model.OrderDetails
import model.Product

class OrderDetailsItemAdapter(private val OrderDetailsList: List<OrderDetails> ,private val context: Context) : RecyclerView.Adapter<OrderDetailsItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val oditemname: TextView = itemView.findViewById(R.id.oditemName)
        val odproductid: TextView = itemView.findViewById(R.id.odp)
        val odquantity: TextView = itemView.findViewById(R.id.odq)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderDetailsItemAdapter.ViewHolder {
        val view = try {
            LayoutInflater.from(parent.context).inflate(R.layout.orderdetailslayouts, parent, false)
        } catch (e: Exception) {
            e.printStackTrace()
            View(parent.context)
        }
        return OrderDetailsItemAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return OrderDetailsList.size
    }

    override fun onBindViewHolder(holder: OrderDetailsItemAdapter.ViewHolder, position: Int) {
        val item = OrderDetailsList[position]

        val db : DBhelper = DBhelper(context)

        val productList: List <Product> = db.getAllProducts()
        var productName :String? = null
        productList.map {
            if (it.getProid() == item.getProductId() ){
                productName = it.getProdName()
            }

        }

        holder.oditemname.text = "Item Name: $productName"
        holder.odproductid.text = "Product ID: ${item.getProductId()}"
        holder.odquantity.text = "Quantity: ${item.getQuantity()}"
    }
}
