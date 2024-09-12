package model

import android.content.Context
import android.widget.Toast

// Simple structure
data class CartItem(val product: Product, var quantity: Int)

/**
 * Singleton class for managing the shopping cart.
 */
object ShoppingCartSingleton {
    private var shoppingCart: ShoppingCart = ShoppingCart()

    /**
     * Get the instance of the shopping cart.
     */
    fun getShoppingCartInstance(): ShoppingCart {
        return shoppingCart
    }

    /**
     * Clear the shopping cart instance.
     */
    fun clearShoppingCartInstance() {
        shoppingCart = ShoppingCart()
    }
}

/**
 * Class representing the shopping cart.
 */
class ShoppingCart {
    private val items: MutableList<CartItem> = mutableListOf()

    /**
     * Add an item to the shopping cart.
     */
    fun addItem(product: Product, quantity: Int) {
        val existingCartItem = items.find { it.product == product }

        if (existingCartItem != null) {
            // Product already exists in the cart, update the quantity
            existingCartItem.quantity = quantity
        } else {
            // Product doesn't exist in the cart, add a new CartItem
            val newCartItem = CartItem(product, quantity)
            items.add(newCartItem)
        }
    }

    /**
     * Add an item to the shopping cart with a toast message.
     */
    fun addToBag(product: Product, context: Context) {
        val existingCartItem = items.find { it.product == product }

        if (existingCartItem == null) {
            // Product doesn't exist in the cart, add a new CartItem with quantity 1
            val newCartItem = CartItem(product, 1)
            items.add(newCartItem)
            Toast.makeText(context, "Product is added to cart", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(
                context,
                "Product is already added, add more in the shopping cart",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * Remove an item from the shopping cart.
     */
    fun removeItem(product: Product) {
        val iterator = items.iterator()
        while (iterator.hasNext()) {
            val cartItem = iterator.next()
            if (cartItem.product == product) {
                iterator.remove()
                break
            }
        }
    }

    /**
     * Update the quantity of an item in the shopping cart.
     */
    fun updateItemQuantity(product: Product, newQuantity: Int) {
        val cartItem = items.find { it.product == product }
        cartItem?.let {
            if (newQuantity > 0) {
                it.quantity = newQuantity
            } else {
                removeItem(product)
            }
        }
    }

    /**
     * Get the list of items in the shopping cart.
     */
    fun getItems(): List<CartItem> {
        return items.toMutableList()
    }

    /**
     * Clear all items from the shopping cart.
     */
    fun clearCart() {
        items.clear()
    }

    /**
     * Get the list of products in the shopping cart.
     */
    fun getProducts(): MutableList<Product> {
        return items.map { it.product }.toMutableList()
    }

    /**
     * Get the quantity of a specific product in the shopping cart.
     */
    fun getProductQuantity(product: Product): Int {
        val cartItem = items.find { it.product == product }
        return cartItem?.quantity ?: 0
    }

    /**
     * Calculate the total price of items in the shopping cart.
     */
    fun calculateTotalPrice(): Double {
        var totalPrice = 0.0

        for (cartItem in items) {
            val productPrice = cartItem.product?.getProdPrice() ?: 0.0
            totalPrice += productPrice * cartItem.quantity
        }

        return totalPrice
    }
}
