package model

/**
 * Feedback class represents the feedback model with various properties.
 * It provides getters for accessing the properties.
 */
class Feedback(
    private val feedbackId: Int,
    private val customerId: Int,
    private val customerName: String,
    private val customerEmail: String,
    private val rating: Float,
    private val comment: String
) {
    // Getters for the properties
    fun getFeedbackId(): Int = feedbackId
    fun getCustomerId(): Int = customerId
    fun getCustomerName(): String = customerName
    fun getCustomerEmail(): String = customerEmail
    fun getRating(): Float = rating
    fun getComment(): String = comment
}
