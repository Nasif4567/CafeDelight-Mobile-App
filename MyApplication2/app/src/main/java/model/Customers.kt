package model

import org.mindrot.jbcrypt.BCrypt

/**
 * CustomerSkeleton is a singleton object that holds an instance of the Customers class.
 * It provides methods to access and clear the instance.
 */
object CustomerSkeleton {
    private var customers: Customers = Customers()

    /**
     * Get the singleton instance of the Customers class.
     * @return Customers instance
     */
    fun getInstanceCustomer(): Customers {
        return customers
    }

    /**
     * Clear the singleton instance of the Customers class.
     */
    fun clearIntanceCustomer() {
        customers = Customers()
    }
}

/**
 * Customers class represents the customer model with various properties.
 * It provides multiple constructors for flexibility and encapsulation with getter and setter methods.
 * It also includes functions for password hashing and password checking.
 */
class Customers {
    private var Cusid: Int? = null
    private lateinit var cusFullName: String
    private lateinit var cusEmail: String
    private var cusPhoneNo: Int? = null
    private lateinit var cusUsername: String
    private lateinit var cusPassword: String
    private var cusActive: Boolean = false

    /**
     * Empty constructor for creating an uninitialized Customers object.
     */
    constructor()

    /**
     * Constructor for creating a Customers object with basic information.
     */
    constructor(
        cusFullName: String,
        cusEmail: String,
        cusPhoneNo: Int?,
        cusUsername: String,
        cusPassword: String,
        cusActive: Boolean
    ) {
        this.cusFullName = cusFullName
        this.cusEmail = cusEmail
        this.cusPhoneNo = cusPhoneNo
        this.cusUsername = cusUsername
        this.cusPassword = cusPassword
        this.cusActive = cusActive
    }

    /**
     * Constructor for creating a Customers object with full information, including Cusid.
     */
    constructor(
        cusid: Int,
        cusFullName: String,
        cusEmail: String,
        cusPhoneNo: Int?,
        cusUsername: String,
        cusPassword: String,
        cusActive: Boolean
    ) {
        this.Cusid = cusid
        this.cusFullName = cusFullName
        this.cusEmail = cusEmail
        this.cusPhoneNo = cusPhoneNo
        this.cusUsername = cusUsername
        this.cusPassword = cusPassword
        this.cusActive = cusActive
    }

    // Getter and Setter methods for encapsulation

    fun getCusid(): Int? {
        return Cusid
    }

    fun setCusId(custid: Int) {
        Cusid = custid
    }

    fun getCusFullName(): String {
        return cusFullName
    }

    fun setCusFullName(fullName: String) {
        cusFullName = fullName
    }

    fun getCusEmail(): String {
        return cusEmail
    }

    fun setCusEmail(email: String) {
        cusEmail = email
    }

    fun getCusPhoneNo(): Int? {
        return cusPhoneNo
    }

    fun setCusPhoneNo(phoneNo: Int?) {
        cusPhoneNo = phoneNo
    }

    fun getCusUsername(): String {
        return cusUsername
    }

    fun setCusUsername(username: String) {
        cusUsername = username
    }

    fun getCusPassword(): String {
        return cusPassword
    }

    fun setCusPassword(password: String) {
        cusPassword = password
    }

    fun isCusActive(): Boolean {
        return cusActive
    }

    fun setCusActive(active: Boolean) {
        cusActive = active
    }

    // Function for security
    fun hashPassword(): String {
        return BCrypt.hashpw(cusPassword, BCrypt.gensalt())
    }

    fun checkPassword(plainText: String, hashedPassword: String): Boolean {
        if (!::cusPassword.isInitialized) {
            throw IllegalStateException("cusPassword has not been initialized")
        }
        return BCrypt.checkpw(plainText, hashedPassword)
    }
}
