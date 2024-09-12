package model

import android.net.Uri

/**
 * Product class represents a product model with properties such as proid, prodName, prodPrice, description,
 * productCategory, prodImage, productAvail, and initialQuantity.
 */
class Product {
    private var proid: Int = 0
    private lateinit var prodName: String
    private var prodPrice: Double? = null
    private lateinit var description: String
    private lateinit var productCategory: String
    private var prodImage: Uri? = null
    private var productAvail = false
    private var initialQuantity: Int = 0

    /**
     * Default constructor for creating a Product object.
     */
    constructor()

    /**
     * Constructor for creating a Product object with specified properties.
     *
     * @param prodName The name of the product.
     * @param prodPrice The price of the product.
     * @param description The description of the product.
     * @param productCategory The category of the product.
     * @param prodImage The image URI of the product.
     * @param productAvail The availability status of the product.
     */
    constructor(
        prodName: String,
        prodPrice: Double,
        description: String,
        productCategory: String,
        prodImage: Uri?,
        productAvail: Boolean
    ) {
        this.prodName = prodName
        this.prodPrice = prodPrice
        this.description = description
        this.productCategory = productCategory
        this.prodImage = prodImage
        this.productAvail = productAvail
    }

    /**
     * Constructor for creating a Product object with specified properties including productId.
     *
     * @param productId The unique identifier for the product.
     * @param prodName The name of the product.
     * @param prodPrice The price of the product.
     * @param description The description of the product.
     * @param productCategory The category of the product.
     * @param prodImage The image URI of the product.
     * @param productAvail The availability status of the product.
     */
    constructor(
        productId: Int,
        prodName: String,
        prodPrice: Double,
        description: String,
        productCategory: String,
        prodImage: Uri?,
        productAvail: Boolean
    ) {
        this.proid = productId
        this.prodName = prodName
        this.prodPrice = prodPrice
        this.description = description
        this.productCategory = productCategory
        this.prodImage = prodImage
        this.productAvail = productAvail
    }

    // Getter and Setter methods for encapsulation

    fun getProid(): Int {
        return proid
    }

    fun setProid(newProid: Int) {
        proid = newProid
    }

    fun getProdName(): String {
        return prodName
    }

    fun setProdName(newProdName: String) {
        prodName = newProdName
    }

    fun getProdPrice(): Double? {
        return prodPrice
    }

    fun getProductCategory(): String {
        return productCategory
    }

    fun setProdCategory(prodCategory: String) {
        productCategory = prodCategory
    }

    fun setProdPrice(newProdPrice: Double) {
        prodPrice = newProdPrice
    }

    fun getDescription(): String {
        return description
    }

    fun setDescription(newDescription: String) {
        description = newDescription
    }

    fun getProdImage(): Uri? {
        return prodImage
    }

    fun setProdImage(newProdImage: Uri?) {
        prodImage = newProdImage
    }

    fun getProdAvail(): Boolean {
        return productAvail
    }

    fun setProdAvail(prodAvail: Boolean) {
        productAvail = prodAvail
    }

    fun getInitialQuantity(): Int {
        return initialQuantity
    }
}
