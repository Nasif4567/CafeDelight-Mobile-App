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
 * ShoppingCartViewAdapter
 *
 * This RecyclerView adapter is responsible for displaying the list of products in the shopping cart.
 * It provides a visual representation of each product with its image, name, description, price,
 * and the ability to adjust the quantity.
 *
 * @param ProductList: List of products to be displayed in the shopping cart.
 * @param callback: ShoppingCartCallback interface for handling quantity changes.
 */
class ShoppingCartViewAdapter(private val ProductList: MutableList<Product>, private val callback: ShoppingCartCallback) :
    RecyclerView.Adapter<ShoppingCartViewAdapter.ViewHolder>() {

    /**
     * ShoppingCartCallback
     *
     * Interface defining a callback method for handling quantity changes.
     */
    interface ShoppingCartCallback {
        fun onQuantityChanged()
    }

    /**
     * ViewHolder
     *
     * Inner class representing the view holder for each item in the RecyclerView.
     * It holds references to the layout elements for efficient view recycling.
     *
     * @param itemView: Inflated layout view for a single item in the RecyclerView.
     * @param adapter: Reference to the parent adapter (ShoppingCartViewAdapter).
     */
    class ViewHolder(itemView: View, private val adapter: ShoppingCartViewAdapter) :
        RecyclerView.ViewHolder(itemView) {

        // Layout elements
        val itemImageView: ImageView = itemView.findViewById(R.id.coffeeImage)
        val itemName: TextView = itemView.findViewById(R.id.itemName)
        val itemDescription: TextView = itemView.findViewById(R.id.itemDescription)
        val itemPrice: TextView = itemView.findViewById(R.id.itemPrice)
        val increaseQuantityBtn: Button = itemView.findViewById(R.id.increasequantitybtn)
        val decreaseQuantityBtn: Button = itemView.findViewById(R.id.decreasequantitybtn)
        val quantityText: TextView = itemView.findViewById(R.id.quantityText)
    }

    /**
     * onCreateViewHolder
     *
     * Inflates the layout for a single item view in the RecyclerView.
     *
     * @param parent: The ViewGroup into which the new View will be added.
     * @param viewType: The type of the new View.
     * @return ViewHolder instance representing the inflated layout.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingCartViewAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.itemlistlayout, parent, false)
        return ViewHolder(view, this)
    }

    /**
     * onBindViewHolder
     *
     * Binds data to the views within a ViewHolder based on the item at the specified position.
     *
     * @param holder: The ViewHolder instance to bind data to.
     * @param position: The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ShoppingCartViewAdapter.ViewHolder, position: Int) {
        val item = ProductList[position]

        // Set image, name, description, and price
        holder.itemImageView.setImageURI(item.getProdImage())
        holder.itemName.text = item.getProdName()
        holder.itemDescription.text = item.getDescription()
        holder.itemPrice.text = "${item.getProdPrice()} $"

        // Set initial quantity
        holder.quantityText.text =
            ShoppingCartSingleton.getShoppingCartInstance().getProductQuantity(item).toString()

        // Set click listeners for increase and decrease buttons
        holder.increaseQuantityBtn.setOnClickListener {
            val currentQuantity =
                ShoppingCartSingleton.getShoppingCartInstance().getProductQuantity(item)
            val newQuantity = currentQuantity + 1

            holder.quantityText.text = newQuantity.toString()

            // Store the product in the shopping cart temporarily
            ShoppingCartSingleton.getShoppingCartInstance().addItem(item, newQuantity)
            callback.onQuantityChanged()
        }

        holder.decreaseQuantityBtn.setOnClickListener {
            val currentQuantity =
                ShoppingCartSingleton.getShoppingCartInstance().getProductQuantity(item)
            if (currentQuantity > 0) {
                val newQuantity = currentQuantity - 1
                holder.quantityText.text = newQuantity.toString()
                ShoppingCartSingleton.getShoppingCartInstance().updateItemQuantity(item, newQuantity)
                // Remove the item from the dataset if the quantity becomes zero
                if (newQuantity == 0) {
                    ProductList.remove(item)
                    notifyItemRemoved(position)
                }
                callback.onQuantityChanged()
            }
        }
    }

    /**
     * getItemCount
     *
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in the adapter's data set.
     */
    override fun getItemCount(): Int {
        return ProductList.size
    }
}
