package model

import org.mindrot.jbcrypt.BCrypt

/**
 * AdminSkeleton is a singleton object that holds an instance of the Admin class.
 * It provides methods to access and clear the instance.
 */
object AdminSkeleton {
    private var admin: Admin = Admin()

    /**
     * Get the singleton instance of the Admin class.
     * @return Admin instance
     */
    fun getInstanceAdmin(): Admin {
        return admin
    }

    /**
     * Clear the singleton instance of the Admin class.
     */
    fun clearInstanceAdmin() {
        admin = Admin()
    }
}

/**
 * Admin class represents the administrator model with various properties.
 * It provides multiple constructors for flexibility and encapsulation with getter and setter methods.
 * It also includes functions for password hashing and password checking.
 */
class Admin {
    private var adminActive: Boolean = false
    private lateinit var adminPassword: String
    private lateinit var adminUserName: String
    private var adminPhoneNo: Int? = null
    private lateinit var adminEmail: String
    private lateinit var adminFullName: String
    private var adminId: Int? = null

    /**
     * Empty constructor for creating an uninitialized Admin object.
     */
    constructor()

    /**
     * Constructor for creating an Admin object with basic information.
     */
    constructor(
        adminFullName: String,
        adminEmail: String,
        adminPhoneNo: Int?,
        adminUserName: String,
        adminPassword: String,
        adminActive: Boolean
    ) {
        this.adminFullName = adminFullName
        this.adminEmail = adminEmail
        this.adminPhoneNo = adminPhoneNo
        this.adminUserName = adminUserName
        this.adminPassword = adminPassword
        this.adminActive = adminActive
    }

    /**
     * Constructor for creating an Admin object with full information, including adminId.
     */
    constructor(
        adminId: Int,
        adminFullName: String,
        adminEmail: String,
        adminPhoneNo: Int?,
        adminUserName: String,
        adminPassword: String,
        adminActive: Boolean
    ) {
        this.adminId = adminId
        this.adminFullName = adminFullName
        this.adminEmail = adminEmail
        this.adminPhoneNo = adminPhoneNo
        this.adminUserName = adminUserName
        this.adminPassword = adminPassword
        this.adminActive = adminActive
    }

    // Getter and Setter methods for encapsulation

    fun getAdminId(): Int? {
        return adminId
    }

    fun setAdminId(id: Int) {
        adminId = id
    }

    fun getAdminFullName(): String {
        return adminFullName
    }

    fun setAdminFullName(fullName: String) {
        adminFullName = fullName
    }

    fun getAdminEmail(): String {
        return adminEmail
    }

    fun setAdminEmail(email: String) {
        adminEmail = email
    }

    fun getAdminPhoneNo(): Int? {
        return adminPhoneNo
    }

    fun setAdminPhoneNo(phoneNo: Int?) {
        adminPhoneNo = phoneNo
    }

    fun getAdminUsername(): String {
        return adminUserName
    }

    fun setAdminUsername(username: String) {
        adminUserName = username
    }

    fun getAdminPassword(): String {
        return adminPassword
    }

    fun setAdminPassword(password: String) {
        adminPassword = password
    }

    fun isAdminActive(): Boolean {
        return adminActive
    }

    fun setAdminActive(active: Boolean) {
        adminActive = active
    }

    /**
     * Function to hash the admin password using BCrypt.
     * @return Hashed password
     */
    fun hashPassword(): String {
        return BCrypt.hashpw(adminPassword, BCrypt.gensalt())
    }

    /**
     * Function to check if a plain text password matches the hashed password.
     * @param plainText Plain text password
     * @param hashedPassword Hashed password to compare against
     * @return True if the passwords match, false otherwise
     */
    fun checkPassword(plainText: String, hashedPassword: String): Boolean {
        if (!::adminPassword.isInitialized) {
            throw IllegalStateException("adminPassword has not been initialized")
        }
        return BCrypt.checkpw(plainText, hashedPassword)
    }
}
