package view

import android.Manifest
import android.app.Activity
import android.content.Context
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


//This is a nested class
class HomePageAdapter(private val ProductList : List<Product> ,private val contextt: Context ) : RecyclerView.Adapter<HomePageAdapter.ViewHolder>() {


    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        // All the layout details will be defined here

        val itemImageView :ImageView = itemView.findViewById(R.id.coffeeImage)
        val itemName: TextView = itemView.findViewById(R.id.itemName)
        val itemDescription: TextView = itemView.findViewById(R.id.itemDescription)
        val itemPrice: TextView = itemView.findViewById(R.id.itemPrice)
        val addBtn : Button = itemView.findViewById(R.id.addtobag)
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HomePageAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.homepgaelayout, parent, false)
        return HomePageAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomePageAdapter.ViewHolder, position: Int) {
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

        holder.addBtn.setOnClickListener {
            ShoppingCartSingleton.getShoppingCartInstance().addToBag(item , contextt)
        }


    }

    override fun getItemCount(): Int {
        return ProductList.size
    }


}