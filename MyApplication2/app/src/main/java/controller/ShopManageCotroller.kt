package controller

import android.content.Context
import android.content.Intent
import android.net.Uri
import DBhelper
import model.Product
import view.AdminPanel


/**
 * The `ShopManageCotroller` class provides methods for managing products in the shop,
 * including adding, retrieving, editing, and deleting products.
 */
class ShopManageCotroller {

    /**
     * Adds a product to the database and redirects to the specified activity.
     *
     * @param db The database helper for product insertion.
     * @param product The product to be added.
     * @param currentContext The current context of the application.
     * @param redirect The target activity class to which the redirection should occur.
     * @return A message indicating the result of the product addition process.
     */
    fun addProductOnClickListener(db: DBhelper, product: Product, currentContext: Context, redirect: Class<*>): String {
        val result = db.insertProduct(product)
        var processMessage: String = ""

        if (result != -1L) {
            // Product insertion was successful
            processMessage = "Product Added!"
            val intent = Intent(currentContext, redirect)
            currentContext.startActivity(intent)
        } else {
            processMessage = "Failed to add product"
        }

        return processMessage
    }

    /**
     * Retrieves all products based on the specified category.
     *
     * @param context The context of the application.
     * @param catagory The product category for filtering.
     * @return A list of products filtered by the specified category.
     */
    fun getAllProductByCatagory(context: Context, catagory: String): List<Product> {
        return DBhelper(context).getAllProductsByCategory(catagory)
    }

    companion object {

        /**
         * Retrieves all products from the database.
         *
         * @param context The context of the application.
         * @return A mutable list of all products in the database.
         */
        fun getAllProduct(context: Context): MutableList<Product> {
            return DBhelper(context).getAllProducts()
        }

        /**
         * Edits a product in the database based on the provided input.
         *
         * @param context The context of the application.
         * @param productId The ID of the product to be edited.
         * @param coffeeName The updated name of the product.
         * @param coffeeDescription The updated description of the product.
         * @param coffeePrice The updated price of the product.
         * @param productCategory The updated category of the product.
         * @param selectedImageUri The updated image URI of the product.
         * @return A message indicating the result of the product editing process.
         */
        fun editProduct(
            context: Context,
            productId: Int,
            coffeeName: String,
            coffeeDescription: String,
            coffeePrice: String,
            productCategory: String,
            selectedImageUri: Uri?
        ): String {
            // Validation
            if (coffeeName.isBlank() || coffeeDescription.isBlank() || coffeePrice.isBlank() || productCategory.isBlank() || selectedImageUri == null) {
                return "Invalid input. Please fill in all fields and provide a valid image."
            }

            try {
                // Parsing price to double
                val parsedPrice = coffeePrice.toDoubleOrNull()

                if (parsedPrice != null && parsedPrice > 0) {
                    // Update the product in the database
                    DBhelper(context).updateProduct(
                        productId,
                        coffeeName,
                        coffeeDescription,
                        parsedPrice,
                        productCategory,
                        selectedImageUri
                    )
                    // Redirect to AdminPanel
                    RedirectionController().Redirect(context, AdminPanel::class.java)
                    return "Product updated successfully"
                } else {
                    return "Invalid price. Please provide a valid positive numeric value."
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return "An error occurred while updating the product: ${e.message}"
            }
        }

        /**
         * Deletes a product from the database based on the provided product ID.
         *
         * @param productid The ID of the product to be deleted.
         * @param context The context of the application.
         * @return A message indicating the result of the product deletion process.
         */
        fun deleteItem(productid: Int, context: Context): String {
            try {
                // Delete the product from the database
                DBhelper(context).deleteProduct(productid)
                return "Product Deleted"
            } catch (e: Exception) {
                return "An error occurred while deleting the product: ${e.message}"
            }
        }
    }
}