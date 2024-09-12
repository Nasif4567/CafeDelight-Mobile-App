package view

import DBhelper
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import controller.ShopManageCotroller
import model.Product


/**
 * HotCoffeeFragment
 *
 * This fragment is part of the CoffeeFragmentAdapter and represents the fragment for displaying hot coffee products.
 */
class HotCoffeeFragment : Fragment() {

    /**
     * onCreate
     *
     * Called to do initial creation of the fragment.
     *
     * @param savedInstanceState If the fragment is being re-created from a previous saved state, this is the state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * onCreateView
     *
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return Return the View for the fragment's UI, or null.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_hot_coffee, container, false)

        // Find the RecyclerView in the layout
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        // Populate the product list for hot coffee
        val productList: List<Product> = ShopManageCotroller().getAllProductByCatagory(requireContext() , "Hot")

        // Set up the RecyclerView with a layout manager and adapter
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        val adapter = HomePageAdapter(productList, requireContext())
        recyclerView.adapter = adapter

        return view
    }
}
