package view

import DBhelper
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import controller.OrderManagerController
import model.Order


class ManageOrderFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_manage, container, false)
        val noorderview:TextView = view.findViewById(R.id.noOrderTextView)

        try {
            val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewod)
            // populate the product list
            val orderlist: List<Order> = OrderManagerController.getAllOrder(requireContext())

            if(orderlist.isEmpty()){
                noorderview.visibility = View.VISIBLE
            }



            val layoutManager = LinearLayoutManager(requireContext())
            recyclerView.layoutManager = layoutManager
            val adapter = OrderItemAdapter(orderlist, requireContext())
            recyclerView.adapter = adapter
        } catch (e: Exception) {
            // Handle the exception here, you can log it or show an error message
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
        }

        return view
    }

}