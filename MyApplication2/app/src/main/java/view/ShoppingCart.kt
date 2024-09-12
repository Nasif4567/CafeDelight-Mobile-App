package view

import DBhelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import controller.RedirectionController
import controller.ShoppingCartController
import model.Product
import model.ShoppingCartSingleton
import java.text.DecimalFormat


/**
 * ShoppingCart Activity
 *
 * This activity represents the shopping cart, displaying the selected products,
 * their quantities, and providing options for checkout.
 */
class ShoppingCart : AppCompatActivity(), ShoppingCartViewAdapter.ShoppingCartCallback {

    // UI elements and data properties
    private lateinit var cartproductlist: List<Product>
    private lateinit var totalcost: TextView
    private lateinit var checkout: Button
    private lateinit var emptycarttextview : TextView
    private lateinit var mycarttile : TextView
    private lateinit var recyclerView: RecyclerView

    /**
     * onCreate function
     *
     * Called when the activity is created. Initializes UI elements, populates the
     * product list from the controller, configures the RecyclerView, sets up the
     * checkout button, and updates the total cost.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)

        // Initialize UI elements
        recyclerView = findViewById(R.id.recyclerViewsh)
        totalcost = findViewById(R.id.totalCost)
        checkout = findViewById(R.id.checkoutButton)
        emptycarttextview = findViewById(R.id.emptyCartTextView)
        mycarttile = findViewById(R.id.mycarttitle)

        // Populate the product list from the ShoppingCartController
        cartproductlist = ShoppingCartController.getShoppingCartItems()

        // Configure the RecyclerView and its adapter
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val adapter = ShoppingCartViewAdapter(cartproductlist as MutableList<Product>, this)
        recyclerView.adapter = adapter

        // Update the total cost display
        updatethetotalcost()

        // Check if the cart is empty and set up the checkout button accordingly
        if (cartproductlist.isEmpty()){
            emptyView()
        } else {
            // Set up the checkout button with a confirmation dialog
            checkout.setOnClickListener {
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("Checkout Confirmation")
                alertDialogBuilder.setMessage("Are you sure you want to proceed with the checkout?")
                alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
                    // Perform the checkout action
                    val processMessage: String = ShoppingCartController.checkout(this)
                    ShowToast.showShortToast(this, processMessage)
                }
                // Set negative button (No) action
                alertDialogBuilder.setNegativeButton("No") { _, _ ->
                }

                val alertDialog: AlertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
        }
    }

    /**
     * emptyView function
     *
     * Hides views (e.g., checkout button, total cost, RecyclerView) when the cart is empty.
     */
    fun emptyView(){
        checkout.visibility = View.GONE
        totalcost.visibility = View.GONE
        emptycarttextview.visibility = View.VISIBLE
        mycarttile.visibility = View.GONE
        recyclerView.visibility = View.GONE
    }

    /**
     * onQuantityChanged function
     *
     * Callback function invoked when the quantity of items in the cart changes.
     * Updates the total cost display.
     */
    override fun onQuantityChanged() {
        updatethetotalcost()
    }

    /**
     * updatethetotalcost function
     *
     * Updates and displays the total cost based on the items in the shopping cart.
     * If the total is zero, hides views using the emptyView function.
     */
    fun updatethetotalcost() {
        val sum = ShoppingCartSingleton.getShoppingCartInstance().calculateTotalPrice()
        // Format the total with two decimal places
        val formattedTotal = DecimalFormat("#.##").format(sum)
        if (formattedTotal.toInt() == 0){
            emptyView()
        }
        totalcost.text = "Total : ${formattedTotal.toString()} $"
    }

    /**
     * onBackPressed function
     *
     * Overrides the back button press behavior. If the cart is empty, redirects to the
     * HomePage. Otherwise, shows a confirmation dialog for navigation, and clears the cart
     * if the user chooses to go back.
     */
    override fun onBackPressed() {
        if (cartproductlist.isEmpty()){
            RedirectionController().Redirect(this , HomePage::class.java)
            ShoppingCartSingleton.clearShoppingCartInstance()
        } else {
            MaterialAlertDialogBuilder(this)
                .setTitle("Confirmation")
                .setMessage("Are you sure you want to go back? Your items will be lost !")
                .setPositiveButton("Yes") { dialog, which ->
                    // Redirect to HomePage and clear the shopping cart
                    RedirectionController().Redirect(this , HomePage::class.java)
                    ShoppingCartSingleton.clearShoppingCartInstance() // Uncomment if needed
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}




