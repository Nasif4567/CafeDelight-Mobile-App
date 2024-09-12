package view

import DBhelper
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import controller.RedirectionController
import controller.ShopManageCotroller
import model.Product

/**
 * AddItemPage
 *
 * This activity allows the user, particularly an admin, to add a new coffee item to the shop's inventory.
 * The user can input details such as coffee name, description, price, and select a category from a spinner.
 * Additionally, the user can choose an image for the coffee item from the device's gallery. Upon completion,
 * the user can click the "Add Coffee" button to add the new coffee item to the shop's inventory.
 */
class AddItemPage : AppCompatActivity() {

    private val db = DBhelper(this)
    private var imagePickerLauncher: ActivityResultLauncher<Intent>? = null
    private var selectedImageUri: Uri? = null
    private lateinit var selectedCategory: String

    /**
     * onCreate
     *
     * Called when the activity is first created. This is where the UI components are initialized,
     * including buttons, text fields, and the image picker. The spinner is set up for selecting
     * the coffee category. Click listeners are added to the buttons to handle image selection and
     * adding a new coffee item.
     *
     * @param savedInstanceState: If non-null, this activity is being re-constructed from a previous
     *                             saved state as given here.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item_page)

        // Spinner code
        val spinner: Spinner = findViewById(R.id.spinnerCoffeeCategory)
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this,
            R.array.coffee_categories_array,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                // Get the selected item from the adapter
                selectedCategory = parentView?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }

        val editTextCoffeeName: EditText = findViewById(R.id.editTextCoffeeName)
        val editTextCoffeeDescription: EditText = findViewById(R.id.editTextCoffeeDescription)
        val editTextCoffeePrice: EditText = findViewById(R.id.editTextCoffeePrice)
        val btnChooseImage: Button = findViewById(R.id.btnChooseImage)
        val btnAddCoffee: Button = findViewById(R.id.btneditCoffee)

        // Library is used to pick images
        imagePickerLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    handleImageSelectionResult(data)
                }
            }

        // This is used to get an image
        btnChooseImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            imagePickerLauncher?.launch(intent)
        }

        btnAddCoffee.setOnClickListener {
            val coffeeName = editTextCoffeeName.text.toString()
            val coffeeDescription = editTextCoffeeDescription.text.toString()
            val coffeePrice = editTextCoffeePrice.text.toString()

            // Validate if EditText fields are not empty
            if (coffeeName.isEmpty() || coffeeDescription.isEmpty() || coffeePrice.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener  // Exit the click listener if validation fails
            }

            // Validate if an image is selected
            if (selectedImageUri == null) {
                Toast.makeText(this, "Please choose an image", Toast.LENGTH_SHORT).show()
                return@setOnClickListener  // Exit the click listener if validation fails
            }

            val spinner: Spinner = findViewById(R.id.spinnerCoffeeCategory)

            val manage = ShopManageCotroller()
            val price: Double = coffeePrice.toDoubleOrNull() ?: 0.0
            val products = Product(
                coffeeName,
                price,
                coffeeDescription,
                selectedCategory,
                selectedImageUri,
                true
            )
            var ProcessMessage =
                manage.addProductOnClickListener(db, products, this, AdminPanel::class.java)
            ShowToast.showShortToast(this, ProcessMessage)
        }
    }

    /**
     * handleImageSelectionResult
     *
     * Handles the result obtained from the image selection process. If a valid image is selected,
     * the selected image URI is set, and the ImageView is updated to display the selected image.
     *
     * @param data: The Intent containing the result data from the image selection.
     */
    private fun handleImageSelectionResult(data: Intent?) {
        if (data != null) {
            selectedImageUri = data.data
            val imageViewCoffee: ImageView = findViewById(R.id.imageViewCoffee)
            imageViewCoffee.setImageURI(selectedImageUri)
        }
    }

    /**
     * onBackPressed
     *
     * Overrides the default behavior when the back button is pressed. Redirects the user to the
     * AdminPanel activity.
     */
    override fun onBackPressed() {
        RedirectionController().Redirect(this, AdminPanel::class.java)
    }
}
