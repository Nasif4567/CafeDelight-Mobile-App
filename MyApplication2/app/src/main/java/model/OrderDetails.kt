package model

/**
 * Class representing order details.
 */
class OrderDetails {
    private var orderDetailsId: Int? = null
    private var orderId: Int? = null
    private var productId: Int? = null
    private var quantity: Int? = null
    private lateinit var productName: String

    /**
     * Constructor for order details.
     */
    constructor(orderId: Int, productId: Int, quantity: Int?) {
        this.orderDetailsId = orderDetailsId
        this.orderId = orderId
        this.productId = productId
        this.quantity = quantity
    }

    /**
     * Constructor for order details with product name.
     */
    constructor(orderId: Int, productName: String, productId: Int, quantity: Int?) {
        this.orderId = orderId
        this.productId = productId
        this.quantity = quantity
        this.productName = productName
    }

    /**
     * Get the order details ID.
     */
    fun getOrderDetailsId(): Int? {
        return orderDetailsId
    }

    /**
     * Set the order details ID.
     */
    fun setOrderDetailsId(orderDetailsId: Int?) {
        this.orderDetailsId = orderDetailsId
    }

    /**
     * Get the order ID.
     */
    fun getOrderId(): Int? {
        return orderId
    }

    /**
     * Set the order ID.
     */
    fun setOrderId(orderId: Int?) {
        this.orderId = orderId
    }

    /**
     * Get the product ID.
     */
    fun getProductId(): Int? {
        return productId
    }

    /**
     * Get the product name.
     */
    fun getProductName(): String {
        return productName
    }

    /**
     * Set the product ID.
     */
    fun setProductId(productId: Int?) {
        this.productId = productId
    }

    /**
     * Get the quantity.
     */
    fun getQuantity(): Int? {
        return quantity
    }

    /**
     * Set the quantity.
     */
    fun setQuantity(quantity: Int?) {
        this.quantity = quantity
    }
}
