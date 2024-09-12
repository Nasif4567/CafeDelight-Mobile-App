package view

import DBhelper
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import controller.RedirectionController
import controller.ShopManageCotroller
import model.Product


/**
 * AdminCoffeeManageFragment
 *
 * This fragment is responsible for displaying the list of coffee items in the admin's coffee management section.
 * It utilizes a RecyclerView to show the coffee items fetched from the database using the ShopManageController.
 * Additionally, it provides a button that allows the admin to navigate to the AddItemPage for adding new coffee items.
 */
class AdminCoffeeManageFragment : Fragment() {

    /**
     * onCreate
     *
     * Called to do initial creation of the fragment. This is where you can instantiate variables,
     * initialize UI components, or perform any setup required.
     *
     * @param savedInstanceState: If non-null, this fragment is being re-constructed from a previous
     *                             saved state as given here.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * onCreateView
     *
     * Called to have the fragment instantiate its user interface view. This is where the UI components
     * are inflated and set up, including the RecyclerView to display the coffee items and the button for
     * navigating to the AddItemPage.
     *
     * @param inflater: The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container: If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState: If non-null, this fragment is being re-constructed from a previous saved state.
     *
     * @return Returns the View for the fragment's UI, or null.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        try {
            // Inflate the layout for this fragment
            val view = inflater.inflate(R.layout.fragment_admin_coffee_manage, container, false)

            val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewad)
            val noItemAddedTextView :TextView = view.findViewById(R.id.noItemAddedTextView)

            // Fetch the list of coffee products from the controller
            val productList: MutableList<Product> = ShopManageCotroller.getAllProduct(requireContext())

            // Show a message if no coffee items are available
            if (productList.isEmpty()) {
                noItemAddedTextView.visibility = View.VISIBLE
            }

            // Set up the RecyclerView with an adapter to display the coffee items
            val layoutManager = LinearLayoutManager(requireContext())
            recyclerView.layoutManager = layoutManager
            val adapter = AdminProductViewAdapter(productList)
            recyclerView.adapter = adapter

            // Set up the button to navigate to the AddItemPage
            var addNewItemButton: Button = view.findViewById(R.id.addNewItemButton)
            addNewItemButton.setOnClickListener {
                RedirectionController().Redirect(requireContext(), AddItemPage::class.java)
            }

            return view
        } catch (e: Exception) {
            // Handle exceptions if any
            e.printStackTrace()
            // Display an error message or handle it as per your application's requirement
            return null
        }
    }
}

