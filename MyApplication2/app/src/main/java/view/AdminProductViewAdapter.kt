package view

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import model.Product
import android.content.Intent
import controller.ShopManageCotroller

/**
 * AdminProductViewAdapter
 *
 * This RecyclerView Adapter is responsible for managing the view of products in the admin's coffee management
 * fragment. It displays product information and provides functionalities such as editing and deleting items.
 */
class AdminProductViewAdapter(private var ProductList: MutableList<Product>) :
    RecyclerView.Adapter<AdminProductViewAdapter.ViewHolder>() {

    /**
     * ViewHolder
     *
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     *
     * @param itemView: The view of the item.
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // All the layout details will be defined here
        val itemImageView: ImageView = itemView.findViewById(R.id.coffeeImagead)
        val itemName: TextView = itemView.findViewById(R.id.itemNamead)
        val itemDescription: TextView = itemView.findViewById(R.id.itemDescriptionad)
        val itemPrice: TextView = itemView.findViewById(R.id.itemPricead)
        val itemCata: TextView = itemView.findViewById(R.id.itemCatagoryad)
        val editbtn: ImageView = itemView.findViewById(R.id.editbtnRedirect)
        val deletebtn: ImageView = itemView.findViewById(R.id.deletebtn)
    }

    /**
     * onCreateViewHolder
     *
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent: The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType: The view type of the new View.
     * @return ViewHolder: A new ViewHolder that holds the View for the given viewType.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): AdminProductViewAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adminviewitemlayout, parent, false)
        return AdminProductViewAdapter.ViewHolder(view)
    }

    /**
     * onBindViewHolder
     *
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder: The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position: The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: AdminProductViewAdapter.ViewHolder, position: Int) {
        try {
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
            holder.itemPrice.text = item.getProdPrice().toString() + "$"
            holder.itemCata.text = item.getProductCategory()

            // Set click listeners for edit and delete buttons
            holder.editbtn.setOnClickListener {
                val intent = Intent(holder.itemView.context, EditItemPage::class.java)
                // Pass product details to EditItemPage
                intent.putExtra("productId", item.getProid())
                intent.putExtra("productName", item.getProdName())
                intent.putExtra("productDescription", item.getDescription())
                intent.putExtra("productPrice", item.getProdPrice())
                intent.putExtra("productCategory", item.getProductCategory())
                intent.putExtra("productImageUri", item.getProdImage().toString())

                holder.itemView.context.startActivity(intent)
            }

            holder.deletebtn.setOnClickListener {
                // Confirmation dialog for item deletion
                val builder = AlertDialog.Builder(holder.itemView.context)
                builder.setTitle("Confirm Deletion")
                builder.setMessage("Are you sure you want to delete this item?")

                builder.setPositiveButton("Yes") { dialog, _ ->
                    // Delete the item from the database and update the RecyclerView
                    var ProcessMessage =
                        ShopManageCotroller.deleteItem(item.getProid(), holder.itemView.context)

                    if (ProcessMessage.equals("Product Deleted")) {
                        ProductList.removeAt(holder.adapterPosition)

                        // Notify the adapter that the data set has changed after deletion
                        notifyItemRemoved(holder.adapterPosition)
                        notifyItemRangeChanged(holder.adapterPosition, ProductList.size)
                    }

                    ShowToast.showShortToast(holder.itemView.context, ProcessMessage)

                    dialog.dismiss()
                }

                builder.setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }

                val alertDialog = builder.create()
                alertDialog.show()
            }

        } catch (e: Exception) {
            // Handle exceptions if any
            e.printStackTrace()
            Toast.makeText(
                holder.itemView.context,
                "An error occurred while processing the item",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * getItemCount
     *
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return Int: The total number of items.
     */
    override fun getItemCount(): Int {
        return ProductList.size
    }
}

