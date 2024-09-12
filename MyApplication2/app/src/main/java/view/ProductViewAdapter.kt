package view

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.myapplication.R
import model.Product
import model.ShoppingCartSingleton
import model.ShoppingCart
import kotlin.math.log


/**
 * ProductViewAdapter
 *
 * Adapter class for RecyclerView to display a list of products. Manages the view for each item
 * in the list, including handling user interactions.
 *
 * @property ProductList The list of products to be displayed in the RecyclerView.
 */
class ProductViewAdapter(private val ProductList: List<Product>) :
    RecyclerView.Adapter<ProductViewAdapter.ViewHolder>() {

    /**
     * ViewHolder
     *
     * Represents a single item view in the RecyclerView. Holds references to the layout elements
     * for efficient data binding.
     *
     * @property itemView The view for a single item in the RecyclerView.
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // All the layout details will be defined here

        val itemImageView: ImageView = itemView.findViewById(R.id.coffeeImage)
        val itemName: TextView = itemView.findViewById(R.id.itemName)
        val itemDescription: TextView = itemView.findViewById(R.id.itemDescription)
        val itemPrice: TextView = itemView.findViewById(R.id.itemPrice)
        val increaseQuantityBtn: Button = itemView.findViewById(R.id.increasequantitybtn)
        val decreaseQuantityBtn: Button = itemView.findViewById(R.id.decreasequantitybtn)
        val quantityText: TextView = itemView.findViewById(R.id.quantityText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.itemlistlayout,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = ProductList[position]

        // Check if the app has permission to read external storage
        if (ContextCompat.checkSelfPermission(
                holder.itemView.context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // You have permission, load the image
            holder.itemImageView.setImageURI(item.getProdImage())
        } else {
            // Request the permission
            ActivityCompat.requestPermissions(
                holder.itemView.context as Activity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                123
            )
        }

        // Bind data to views
        holder.itemName.text = item.getProdName()
        holder.itemDescription.text = item.getDescription()
        holder.itemPrice.text = item.getProdPrice().toString() + " $"

        // Set initial quantity
        holder.quantityText.text = item.getInitialQuantity().toString()

        // Set click listeners for increase and decrease buttons
        holder.increaseQuantityBtn.setOnClickListener {
            val currentQuantity = holder.quantityText.text.toString().toInt()
            val newQuantity = currentQuantity + 1
            holder.quantityText.text = newQuantity.toString()

            // Store the product in the shopping cart temporarily
            ShoppingCartSingleton.getShoppingCartInstance().addItem(item, newQuantity)
        }

        holder.decreaseQuantityBtn.setOnClickListener {
            val currentQuantity = holder.quantityText.text.toString().toInt()
            if (currentQuantity > 0) {
                val newQuantity = currentQuantity - 1
                holder.quantityText.text = newQuantity.toString()
                ShoppingCartSingleton.getShoppingCartInstance().updateItemQuantity(item, newQuantity)
            }
        }
    }

    override fun getItemCount(): Int {
        return ProductList.size
    }
}
