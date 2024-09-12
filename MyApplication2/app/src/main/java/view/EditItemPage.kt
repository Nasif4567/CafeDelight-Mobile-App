package view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import DBhelper
import com.example.myapplication.MainActivity
import controller.RedirectionController
import controller.ShopManageCotroller

class EditItemPage : AppCompatActivity() {

    private lateinit var db: DBhelper
    private var selectedImageUri: Uri? = null
    private lateinit var imgview: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item_page)
        db = DBhelper(this)

        try {
            val productId = intent.getIntExtra("productId", -1)
            val productName = intent.getStringExtra("productName")
            val productDescription = intent.getStringExtra("productDescription")
            val productPrice = intent.getDoubleExtra("productPrice", 0.0)
            val productCategory = intent.getStringExtra("productCategory")

            val productImageUriString = intent.getStringExtra("productImageUri")
            val productImageUri = Uri.parse(productImageUriString)

            val editnametxt: EditText = findViewById(R.id.editTextCoffeeName)
            val editnamedes: EditText = findViewById(R.id.editTextCoffeeDescription)
            val editpricetxt: EditText = findViewById(R.id.editTextCoffeePrice)
            imgview = findViewById(R.id.eimageViewCoffee) // Initialize the ImageView

            editnametxt.setText(productName)
            editnamedes.setText(productDescription)
            editpricetxt.setText(productPrice.toString())
            imgview.setImageURI(productImageUri)

            val imgchoose: Button = findViewById(R.id.ebtnChooseImage)
            imgchoose.setOnClickListener {
                openImagePicker()
            }

            val save: Button = findViewById(R.id.btneditCoffee)
            save.setOnClickListener {
                    val coffeeName = editnametxt.text.toString().trim()
                    val coffeeDescription = editnamedes.text.toString().trim()
                    val coffeePriceText = editpricetxt.text.toString().trim()

                    var ProcessMessage : String = ""

                    val coffeePrice = coffeePriceText.toString()
                    if (productCategory != null) {
                        ProcessMessage =  ShopManageCotroller.editProduct(this, productId, coffeeName, coffeeDescription, coffeePrice, productCategory, selectedImageUri ?: productImageUri)
                    }

                    ShowToast.showShortToast(this ,ProcessMessage)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openImagePicker() {
        try {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_PICK_REQUEST_CODE)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "An error occurred while opening the image picker", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == RESULT_OK) {
            try {
                selectedImageUri = data?.data
                imgview.setImageURI(selectedImageUri)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "An error occurred while processing the selected image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val IMAGE_PICK_REQUEST_CODE = 123
    }


    override fun onBackPressed() {
        RedirectionController().Redirect(this , AdminPanel::class.java)
    }
}
