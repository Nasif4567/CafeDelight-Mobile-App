import android.annotation.SuppressLint
import android.content.Context
import android.content.ContentValues
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.util.Log
import model.Admin
import model.AdminSkeleton
import model.CustomerSkeleton
import model.Customers
import model.Feedback
import model.Order
import model.OrderDetails
import model.Product

class DBhelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Defining the database schema and version
    companion object {
        const val DATABASE_NAME = "CoffeeDatabaseName.db"
        const val DATABASE_VERSION = 1


        // Table names
        const val TABLE_CUSTOMERS = "Customers"
        const val TABLE_ADMIN = "Admin"
        const val TABLE_PRODUCT = "Product"
        const val TABLE_ORDER ="Order"
        const val TABLE_ORDER_DETAILS ="OrderDetails"
        const val TABLE_PAYMENT = "Payment"

        // CUSOTOMER column names
        const val CUS_COLUMN_ID = "Cusid"
        const val CUS_COLUMN_NAME = "CusFullName"
        const val CUS_COLUMN_EMAIL = "CusEmail"
        const val CUS_COLUMN_PHONE = "CusPhoneNo"
        const val CUS_COLUMN_USERNAME = "CusUserName"
        const val CUS_COLUMN_PASSWORD = "CusPassword"
        const val CUS_COLUMN_ACTIVE = "CusIsActive"


        // ADMIN column names
        const val ADMIN_COLUMN_ID = "Adminid"
        const val ADMIN_COLUMN_NAME = "AdminFullName"
        const val ADMIN_COLUMN_EMAIL = "AdminEmail"
        const val ADMIN_COLUMN_PHONE = "AdminPhoneNo"
        const val ADMIN_COLUMN_USERNAME = "AdminUserName"
        const val ADMIN_COLUMN_PASSWORD = "AdminPassword"
        const val ADMIN_COLUMN_ACTIVE = "AdminIsActive"


        //PRODUCT coloumn name

        const val PRODUCT_COLUMN_ID = "Prodid"
        const val PRODUCT_COLUMN_NAME = "ProdName"
        const val PRODUCT_COLUMN_DESCRIPTION ="ProdDes"
        const val PRODUCT_COLUMN_CATAGORY ="ProdCatagory"
        const val PRODUCT_COLUMN_PRICE = "ProdPrice"
        const val PRODUCT_COLUMN_IMAGE ="ProdImage"
        const val PRODUCT_COLUMN_AVAIL ="ProdAvail"


        const val order_column_id = "Orderid"
        const val order_column_cusid = "Cusid"
        const val order_column_date = "OrderDate"
        const val order_column_time = "OrderTime"
        const val order_column_status ="OrderStatus"


        const val orderDetails_column_id = "OrderDetailsid"
        const val orderDetails_column_orderID="Orderid"
        const val orderDetails_column_prodID = "Prodid"
        const val orderDetails_columns_quantity = "Quantity"

        const val paymnet_column_paymentid = "Paymentid"
        const val payment_column_orderID="Orderid"
        const val payment_column_paymenttype="PaymentType"
        const val payment_column_amount="Amount"
        const val payment_column_date = "PaymentDate"

        const val TABLE_FEEDBACK = "Feedback"
        const val FEEDBACK_COLUMN_ID = "FeedbackId"
        const val FEEDBACK_COLUMN_CUS_ID = "CusId"
        const val FEEDBACK_COLUMN_CUS_NAME = "CusName"
        const val FEEDBACK_COLUMN_CUS_EMAIL = "CusEmail"
        const val FEEDBACK_COLUMN_RATING = "Rating"
        const val FEEDBACK_COLUMN_COMMENT = "Comment"




        // SQL statement to create the Customers table
        private val CREATE_CUSTOMERS_TABLE = """
        CREATE TABLE $TABLE_CUSTOMERS (
            $CUS_COLUMN_ID  INTEGER PRIMARY KEY AUTOINCREMENT,
            $CUS_COLUMN_NAME TEXT,
            $CUS_COLUMN_EMAIL TEXT,
            $CUS_COLUMN_PHONE INTEGER,
            $CUS_COLUMN_USERNAME TEXT,
            $CUS_COLUMN_PASSWORD TEXT,
            $CUS_COLUMN_ACTIVE BOOLEAN
        )
    """.trimIndent()


        private val CREATE_ADMIN_TABLE = """
    CREATE TABLE $TABLE_ADMIN (
        $ADMIN_COLUMN_ID  INTEGER PRIMARY KEY AUTOINCREMENT,
        $ADMIN_COLUMN_NAME TEXT,
        $ADMIN_COLUMN_EMAIL TEXT,
        $ADMIN_COLUMN_PHONE INTEGER,
        $ADMIN_COLUMN_USERNAME TEXT,
        $ADMIN_COLUMN_PASSWORD TEXT,
        $ADMIN_COLUMN_ACTIVE BOOLEAN
    )
""".trimIndent()

        private val CREATE_PRODUCT_TABLE = """
    CREATE TABLE $TABLE_PRODUCT (
        $PRODUCT_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $PRODUCT_COLUMN_NAME TEXT,
        $PRODUCT_COLUMN_DESCRIPTION TEXT,
        $PRODUCT_COLUMN_CATAGORY TEXT,
        $PRODUCT_COLUMN_PRICE REAL,
        $PRODUCT_COLUMN_IMAGE BLOB,
        $PRODUCT_COLUMN_AVAIL BOOLEAN
    )
""".trimIndent()

        private val CREATE_ORDER_TABLE = """
    CREATE TABLE "$TABLE_ORDER" (
        $order_column_id INTEGER PRIMARY KEY AUTOINCREMENT,
        $order_column_cusid INTEGER,
        $order_column_date TEXT,
        $order_column_time TEXT,
        $order_column_status TEXT
    )
""".trimIndent()

        private val CREATE_ORDER_DETAILS_TABLE = """
    CREATE TABLE $TABLE_ORDER_DETAILS (
        $orderDetails_column_id INTEGER PRIMARY KEY AUTOINCREMENT,
        $orderDetails_column_orderID INTEGER,
        $orderDetails_column_prodID INTEGER,
        $orderDetails_columns_quantity INTEGER 
    )
""".trimIndent()

        private val CREATE_PAYMENT_TABLE = """
    CREATE TABLE $TABLE_PAYMENT (
        $paymnet_column_paymentid INTEGER PRIMARY KEY AUTOINCREMENT,
        $payment_column_orderID INTEGER,
        $payment_column_paymenttype TEXT,
        $payment_column_amount REAL,
        $payment_column_date TEXT
    )
""".trimIndent()



        private val CREATE_FEEDBACK_TABLE = """
    CREATE TABLE $TABLE_FEEDBACK (
        $FEEDBACK_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $FEEDBACK_COLUMN_CUS_ID INTEGER,
        $FEEDBACK_COLUMN_CUS_NAME TEXT,
        $FEEDBACK_COLUMN_CUS_EMAIL TEXT,
        $FEEDBACK_COLUMN_RATING REAL,
        $FEEDBACK_COLUMN_COMMENT TEXT
    )
""".trimIndent()



    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_CUSTOMERS_TABLE)
        db?.execSQL(CREATE_ADMIN_TABLE)
        db?.execSQL(CREATE_PRODUCT_TABLE)
        db?.execSQL(CREATE_ORDER_TABLE)
        db?.execSQL(CREATE_ORDER_DETAILS_TABLE)
        db?.execSQL(CREATE_PAYMENT_TABLE)
        db?.execSQL(CREATE_FEEDBACK_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CUSTOMERS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCT")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ORDER_DETAILS")
        db?.execSQL("DROP TABLE IF EXISTS \"$TABLE_ORDER\"")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_FEEDBACK ")

    }



    // The operations insert , read , delete , read

//-----------------------------------Admin Operation------------------------------------------------------

    // Helper method to convert Admin object to ContentValues
    private fun getContentValuesAdmin(admin: Admin): ContentValues {
        val values = ContentValues()
        values.put(ADMIN_COLUMN_NAME, admin.getAdminFullName())
        values.put(ADMIN_COLUMN_EMAIL, admin.getAdminEmail())
        values.put(ADMIN_COLUMN_PHONE, admin.getAdminPhoneNo())
        values.put(ADMIN_COLUMN_USERNAME, admin.getAdminUsername())
        values.put(ADMIN_COLUMN_PASSWORD, admin.hashPassword()) // Hash the password
        values.put(ADMIN_COLUMN_ACTIVE, admin.isAdminActive())
        return values
    }

    // Insert operation for Admin
    fun insertAdmin(admin: Admin): Long {
        val db = writableDatabase
        val values = getContentValuesAdmin(admin)

        return try {
            db.insert(TABLE_ADMIN, null, values)
        } catch (e: SQLException) {
            -1L
        } finally {
            db.close()
        }
    }

    fun isAdminUnique(username: String, email: String, phoneNumber: Int?): String {
        val db = readableDatabase
        var cursor: Cursor? = null // Initialize the cursor

        try {
            // Check if admin username is unique
            cursor = db.rawQuery("SELECT * FROM $TABLE_ADMIN WHERE $ADMIN_COLUMN_USERNAME = ?", arrayOf(username))
            cursor?.let {
                if (it.count > 0) {
                    return "Username already taken" // Admin username already exists
                }
            }

            // Check if admin email is unique
            cursor?.close() // Close the previous cursor before opening a new one
            cursor = db.rawQuery("SELECT * FROM $TABLE_ADMIN WHERE $ADMIN_COLUMN_EMAIL = ?", arrayOf(email))
            cursor?.let {
                if (it.count > 0) {
                    return "Email already taken" // Admin email already exists
                }
            }

            // Check if admin phone number is unique
            cursor?.close() // Close the previous cursor before opening a new one
            if (phoneNumber != null) {
                cursor = db.rawQuery("SELECT * FROM $TABLE_ADMIN WHERE $ADMIN_COLUMN_PHONE = ?", arrayOf(phoneNumber.toString()))
                cursor?.let {
                    if (it.count > 0) {
                        return "Phone number already taken" // Admin phone number already exists
                    }
                }
            }
        } finally {
            cursor?.close()
            db.close()
        }

        // If no duplicates were found, consider the combination as unique
        return "OK"
    }
    @SuppressLint("Range")
    fun verifyAdminLogin(username: String, password: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT $ADMIN_COLUMN_PASSWORD FROM $TABLE_ADMIN WHERE $ADMIN_COLUMN_USERNAME = ?",
            arrayOf(username)
        )

        cursor?.use {
            if (it.moveToFirst()) {
                val hashedPassword = it.getString(it.getColumnIndex(ADMIN_COLUMN_PASSWORD))
                return AdminSkeleton.getInstanceAdmin().checkPassword(password, hashedPassword)
            }
        }

        return false
    }




    //-----------------------------------Customer Operations----------------------------------------------------
    private fun getContentValuesCustomers(customers: Customers): ContentValues {
        val values = ContentValues()
        values.put(CUS_COLUMN_NAME, customers.getCusFullName())
        values.put(CUS_COLUMN_EMAIL, customers.getCusEmail())
        values.put(CUS_COLUMN_PHONE, customers.getCusPhoneNo())
        values.put(CUS_COLUMN_USERNAME, customers.getCusUsername())
        values.put(CUS_COLUMN_PASSWORD, customers.hashPassword()) // Hash the password
        values.put(CUS_COLUMN_ACTIVE, customers.isCusActive())
        return values
    }

    // insert operation
    fun insertCustomer(customers:Customers): Long {
        var db = writableDatabase
        val values = getContentValuesCustomers(customers)

        return try {
            db.insert(TABLE_CUSTOMERS, null , values)
        }
            catch(e:SQLException){
              -1
            }
        finally {
              db.close()
        }
    }


    fun isCustomerUnique(username: String, email: String, phoneNumber: Int?): String {
        val db = readableDatabase
        var cursor: Cursor? = null

        try {
            // Check if username is unique
            cursor = db.rawQuery("SELECT * FROM $TABLE_CUSTOMERS WHERE $CUS_COLUMN_USERNAME = ?", arrayOf(username))
            cursor?.let {
                if (it.count > 0) {
                    return "Username already taken" // Username already exists
                }
            }

            // Check if email is unique
            cursor?.close() // Close the previous cursor before opening a new one
            cursor = db.rawQuery("SELECT * FROM $TABLE_CUSTOMERS WHERE $CUS_COLUMN_EMAIL = ?", arrayOf(email))
            cursor?.let {
                if (it.count > 0) {
                    return "Email already taken" // Email already exists
                }
            }

            // Check if phone number is unique
            cursor?.close() // Close the previous cursor before opening a new one
            if (phoneNumber != null) {
                cursor = db.rawQuery("SELECT * FROM $TABLE_CUSTOMERS WHERE $CUS_COLUMN_PHONE = ?", arrayOf(phoneNumber.toString()))
                cursor?.let {
                    if (it.count > 0) {
                        return "Phone Number already taken" // Phone number already exists
                    }
                }
            }
        } finally {
            cursor?.close()
            db.close()
        }

        // If no duplicates were found, consider the combination as unique
        return "OK"
    }

    // Read all customer data
    @SuppressLint("Range")
    fun getAllCustomers(): List<Customers> {
        val customers = mutableListOf<Customers>()
        val db = readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery("SELECT * FROM $TABLE_CUSTOMERS", null)
            cursor?.let {
                while (it.moveToNext()) {
                    val customer = Customers(
                        it.getInt(it.getColumnIndex(CUS_COLUMN_ID)),
                        it.getString(it.getColumnIndex(CUS_COLUMN_NAME)),
                        it.getString(it.getColumnIndex(CUS_COLUMN_EMAIL)),
                        it.getInt(it.getColumnIndex(CUS_COLUMN_PHONE)),
                        it.getString(it.getColumnIndex(CUS_COLUMN_USERNAME)),
                        it.getString(it.getColumnIndex(CUS_COLUMN_PASSWORD)),
                        it.getInt(it.getColumnIndex(CUS_COLUMN_ACTIVE)) == 1
                    )
                    customers.add(customer)
                }
                cursor.close()
            }
        } finally {
            db.close()
        }

        return customers
    }


    @SuppressLint("Range")
    fun getCustomerByUsername(username: String): Customers? {
        val db = readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery("SELECT * FROM $TABLE_CUSTOMERS WHERE $CUS_COLUMN_USERNAME = ?", arrayOf(username))
            cursor?.let {
                if (it.moveToFirst()) {
                    return Customers(
                        it.getString(it.getColumnIndex(CUS_COLUMN_NAME)),
                        it.getString(it.getColumnIndex(CUS_COLUMN_EMAIL)),
                        it.getInt(it.getColumnIndex(CUS_COLUMN_PHONE)),
                        it.getString(it.getColumnIndex(CUS_COLUMN_USERNAME)),
                        it.getString(it.getColumnIndex(CUS_COLUMN_PASSWORD)),
                        it.getInt(it.getColumnIndex(CUS_COLUMN_ACTIVE)) == 1
                    )
                }
                cursor.close()
            }
        } finally {
            db.close()
        }

        return null
    }




    // Verify password during login
    @SuppressLint("Range")
    fun verifyLogin(username: String, password: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT $CUS_COLUMN_PASSWORD FROM $TABLE_CUSTOMERS WHERE $CUS_COLUMN_USERNAME = ?",
            arrayOf(username)
        )

        cursor?.use {
            if (it.moveToFirst()) {
                val hashedPassword = it.getString(it.getColumnIndex(CUS_COLUMN_PASSWORD))
                return CustomerSkeleton.getInstanceCustomer().checkPassword(password, hashedPassword)
            }
        }

        return false
    }



    @SuppressLint("Range")
    fun getCustomerIdByUsername(username: String): Int {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT $CUS_COLUMN_ID FROM $TABLE_CUSTOMERS WHERE $CUS_COLUMN_USERNAME = ?",
            arrayOf(username)
        )

        cursor?.use {
            if (it.moveToFirst()) {
                return it.getInt(it.getColumnIndex(CUS_COLUMN_ID))
            }
        }

        return 0
    }


//-----------------------------------------------Product operation -------------------------------------------------------
// Helper method to convert Product object to ContentValues


    private fun getContentValuesProducts(product: Product): ContentValues {
        val values = ContentValues()
        values.put(PRODUCT_COLUMN_NAME, product.getProdName())
        values.put(PRODUCT_COLUMN_DESCRIPTION, product.getDescription())
        values.put(PRODUCT_COLUMN_CATAGORY, product.getProductCategory())
        values.put(PRODUCT_COLUMN_PRICE, product.getProdPrice())
        values.put(PRODUCT_COLUMN_IMAGE, product.getProdImage().toString())
        values.put(PRODUCT_COLUMN_AVAIL, product.getProdAvail())
        return values
    }



    fun insertProduct(product: Product): Long {
        val db = writableDatabase
        val values = getContentValuesProducts(product)

        return try {
            db.insert(TABLE_PRODUCT, null, values)
        } catch (e: SQLException) {
            -1L
        } finally {
            db.close()
        }
    }

    fun updateProduct(
        productId: Int,
        productName: String,
        productDescription: String,
        productPrice: Double,
        productCategory: String,
        productImageUri: Uri
    ): Int {
        val db = writableDatabase
        val values = getContentValuesProducts(
            Product(
                productName, productPrice ,productDescription , productCategory , productImageUri , true
        ))

        return try {
            db.update(
                TABLE_PRODUCT,
                values,
                "$PRODUCT_COLUMN_ID = ?",
                arrayOf(productId.toString())
            )
        } catch (e: SQLException) {
            0 // Return 0 if there's an error
        } finally {
            db.close()
        }
    }

    fun deleteProduct(productId: Int): Int {
        val db = writableDatabase

        return try {
            db.delete(TABLE_PRODUCT, "$PRODUCT_COLUMN_ID = ?", arrayOf(productId.toString()))
        } catch (e: SQLException) {
            0 // Return 0 if there's an error
        } finally {
            db.close()
        }
    }

    @SuppressLint("Range")
    fun getAllProducts(): MutableList<Product> {
        val products = mutableListOf<Product>()
        val db = readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery("SELECT * FROM $TABLE_PRODUCT", null)
            cursor?.let {
                while (it.moveToNext()) {
                    val product = Product(
                        it.getInt(it.getColumnIndex(PRODUCT_COLUMN_ID)),
                        it.getString(it.getColumnIndex(PRODUCT_COLUMN_NAME)),
                        it.getDouble(it.getColumnIndex(PRODUCT_COLUMN_PRICE)),
                        it.getString(it.getColumnIndex(PRODUCT_COLUMN_DESCRIPTION)),
                        it.getString(it.getColumnIndex(PRODUCT_COLUMN_CATAGORY)) ,
                        Uri.parse(it.getString(it.getColumnIndex(PRODUCT_COLUMN_IMAGE))),
                        it.getInt(it.getColumnIndex(PRODUCT_COLUMN_AVAIL)) == 1
                    )
                    products.add(product)
                }
                cursor.close()
            }
        } finally {
            db.close()
        }

        return products
    }

    @SuppressLint("Range")
    fun getProductById(productId: Int): Product? {
        val db = readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery("SELECT * FROM $TABLE_PRODUCT WHERE $PRODUCT_COLUMN_ID = ?", arrayOf(productId.toString()))
            cursor?.let {
                if (it.moveToFirst()) {
                    return Product(
                        it.getString(it.getColumnIndex(PRODUCT_COLUMN_NAME)),
                        it.getDouble(it.getColumnIndex(PRODUCT_COLUMN_PRICE)),
                        it.getString(it.getColumnIndex(PRODUCT_COLUMN_DESCRIPTION)),
                        it.getString(it.getColumnIndex(PRODUCT_COLUMN_CATAGORY)),
                        Uri.parse(it.getString(it.getColumnIndex(PRODUCT_COLUMN_IMAGE))),
                        it.getInt(it.getColumnIndex(PRODUCT_COLUMN_AVAIL)) == 1
                    )
                }
                cursor.close()
            }
        } finally {
            db.close()
        }

        return null
    }


    @SuppressLint("Range")
    fun getAllProductsByCategory(prodCat: String): List<Product> {
        val products = mutableListOf<Product>()
        val db = readableDatabase
        val cursor: Cursor?

        try {
            // Use a parameterized query to avoid SQL injection
            val query = "SELECT * FROM $TABLE_PRODUCT WHERE $PRODUCT_COLUMN_CATAGORY = ?"
            cursor = db.rawQuery(query, arrayOf(prodCat))

            cursor?.let {
                while (it.moveToNext()) {
                    val product = Product(
                        it.getInt(it.getColumnIndex(PRODUCT_COLUMN_ID)),
                        it.getString(it.getColumnIndex(PRODUCT_COLUMN_NAME)),
                        it.getDouble(it.getColumnIndex(PRODUCT_COLUMN_PRICE)),
                        it.getString(it.getColumnIndex(PRODUCT_COLUMN_DESCRIPTION)),
                        it.getString(it.getColumnIndex(PRODUCT_COLUMN_CATAGORY)) ?: "Hot",
                        Uri.parse(it.getString(it.getColumnIndex(PRODUCT_COLUMN_IMAGE))),
                        it.getInt(it.getColumnIndex(PRODUCT_COLUMN_AVAIL)) == 1
                    )
                    products.add(product)
                }
                cursor.close()
            }
        } finally {
            db.close()
        }

        return products
    }




// ------------------------------------------------order operations ----------------------------------------------
// Helper method to convert Order object to ContentValues
private fun getContentValuesOrder(order: Order): ContentValues {
    val values = ContentValues()
    values.put(order_column_cusid, order.getOrderCusId())
    values.put(order_column_date, order.getOrderDate())
    values.put(order_column_time, order.getOrderTime())
    values.put(order_column_status, order.getOrderStatus())
    return values
}

    // Insert operation for Order
    fun insertOrder(order: Order): Long {
        val db = writableDatabase
        val values = getContentValuesOrder(order)

        return try {
            db.insert("\"$TABLE_ORDER\"", null, values)
        } catch (e: SQLException) {
            -1L
        } finally {
            db.close()
        }
    }


    // Delete operation for Order
    fun deleteOrder(orderId: String): Int {
        val db = writableDatabase

        return try {
            db.beginTransaction()

            // Delete from OrderDetails table where order_id matches
            db.delete("\"$TABLE_ORDER_DETAILS\"", "$orderDetails_column_orderID = ?", arrayOf(orderId))

            // Delete from Order table where _id matches
            val rowsAffected = db.delete("\"$TABLE_ORDER\"", "$order_column_id = ?", arrayOf(orderId))

            db.setTransactionSuccessful()
            rowsAffected
        } catch (e: SQLException) {
            0 // Return 0 if there's an error
        } finally {
            db.endTransaction()
            db.close()
        }
    }


    // Get all orders
    @SuppressLint("Range")
    fun getAllOrders(): List<Order> {
        val orders = mutableListOf<Order>()
        val db = readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery("SELECT * FROM \"$TABLE_ORDER\" ", null)
            cursor?.let {
                while (it.moveToNext()) {
                    val order = Order(
                        it.getInt(it.getColumnIndex(orderDetails_column_orderID)),
                        it.getInt(it.getColumnIndex(order_column_cusid)),
                        it.getString(it.getColumnIndex(order_column_date)),
                        it.getString(it.getColumnIndex(order_column_time)),
                        it.getString(it.getColumnIndex(order_column_status))
                    )
                    orders.add(order)
                }
                cursor.close()
            }
        } finally {
            db.close()
        }

        return orders
    }

    @SuppressLint("Range")
    fun getOrderIdByCustomerId(customerId: Int): Int {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT $order_column_id FROM \"$TABLE_ORDER\" WHERE $order_column_cusid = ?",
            arrayOf(customerId.toString())
        )

        cursor?.use {
            if (it.moveToFirst()) {
                return it.getInt(it.getColumnIndex(order_column_id))
            }
        }

        return 0
    }

    fun updateOrderStatus(orderId: Int, newStatus: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(order_column_status, newStatus)
        }
        try {
            db.update( "\"$TABLE_ORDER\"", values, "$order_column_id=?", arrayOf(orderId.toString()))
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            db.close()
        }
    }

    @SuppressLint("Range")
    fun getAllOrdersByCustomerId(customerId: Int?): List<Order> {
        val orders = mutableListOf<Order>()
        val db = readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(
                "SELECT * FROM \"$TABLE_ORDER\" WHERE $order_column_cusid = ?",
                arrayOf(customerId.toString())
            )

            cursor?.let {
                while (it.moveToNext()) {
                    val order = Order(
                        it.getInt(it.getColumnIndex(orderDetails_column_orderID)),
                        it.getInt(it.getColumnIndex(order_column_cusid)),
                        it.getString(it.getColumnIndex(order_column_date)),
                        it.getString(it.getColumnIndex(order_column_time)),
                        it.getString(it.getColumnIndex(order_column_status))
                    )
                    orders.add(order)
                }
                cursor.close()
            }
        } finally {
            db.close()
        }

        return orders
    }





    //-----------------------------------------------------------------------------------------------
    // Order details
    private fun getContentValuesOrderDetails(orderDetails: OrderDetails): ContentValues {
        val values = ContentValues()
        values.put(orderDetails_column_orderID, orderDetails.getOrderId())
        values.put(orderDetails_column_prodID, orderDetails.getProductId())
        values.put(orderDetails_columns_quantity, orderDetails.getQuantity())
        return values
    }

    // Insert operation for OrderDetails
    fun insertOrderDetails(orderDetails: OrderDetails): Long {
        val db = writableDatabase
        val values = getContentValuesOrderDetails(orderDetails)

        return try {
            db.insert(TABLE_ORDER_DETAILS, null, values)
        } catch (e: SQLException) {
            -1L
        } finally {
            db.close()
        }
    }

    // Delete operation for OrderDetails
    fun deleteOrderDetails(orderDetailsId: Int): Int {
        val db = writableDatabase

        return try {
            db.delete(TABLE_ORDER_DETAILS, "$orderDetails_column_id = ?", arrayOf(orderDetailsId.toString()))
        } catch (e: SQLException) {
            0 // Return 0 if there's an error
        } finally {
            db.close()
        }
    }


    @SuppressLint("Range")
    fun getAllOrderDetailsByOrderID(orderID: String): List<OrderDetails> {
        val orderDetailsList = mutableListOf<OrderDetails>()
        val db = readableDatabase
        var cursor: Cursor? = null

        try {
            val query = "SELECT * FROM $TABLE_ORDER_DETAILS WHERE $orderDetails_column_orderID = ?"
            cursor = db.rawQuery(query, arrayOf(orderID.toString()))

            cursor?.let {
                while (it.moveToNext()) {
                    val productId = it.getInt(it.getColumnIndex(orderDetails_column_prodID))
                    val quantity = it.getInt(it.getColumnIndex(orderDetails_columns_quantity))

                    // Get product details using productId
                    val product = getProductById(productId)
                    val productName = product?.getProdName() ?: "Unknown Product"

                    val orderDetails = OrderDetails(
                        it.getInt(it.getColumnIndex(orderDetails_column_orderID)),
                        productName,
                        productId,
                        quantity
                    )
                    orderDetailsList.add(orderDetails)
                }
            }
        } finally {
            cursor?.close()
            db.close()
        }

        return orderDetailsList
    }






//-----------------------------------------FEEDBACK OPERATION--------------------------------------------------------------
// Helper method to convert Feedback object to ContentValues
private fun getContentValuesFeedback(feedback: Feedback): ContentValues {
    val values = ContentValues()
    values.put(FEEDBACK_COLUMN_CUS_ID, feedback.getCustomerId())
    values.put(FEEDBACK_COLUMN_CUS_NAME, feedback.getCustomerName())
    values.put(FEEDBACK_COLUMN_CUS_EMAIL, feedback.getCustomerEmail())
    values.put(FEEDBACK_COLUMN_RATING, feedback.getRating())
    values.put(FEEDBACK_COLUMN_COMMENT, feedback.getComment())
    return values
}

    // Insert operation for Feedback
    fun insertFeedback(feedback: Feedback): Long {
        val db = writableDatabase
        val values = getContentValuesFeedback(feedback)

        return try {
            db.insert(TABLE_FEEDBACK, null, values)
        } catch (e: SQLException) {
            -1L
        } finally {
            db.close()
        }
    }

    // Get all feedback
    @SuppressLint("Range")
    fun getAllFeedback(): MutableList<Feedback> {
        val feedbackList = mutableListOf<Feedback>()
        val db = readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery("SELECT * FROM $TABLE_FEEDBACK", null)
            cursor?.let {
                while (it.moveToNext()) {
                    val feedback = Feedback(
                        it.getInt(it.getColumnIndex(FEEDBACK_COLUMN_ID)),
                        it.getInt(it.getColumnIndex(FEEDBACK_COLUMN_CUS_ID)),
                        it.getString(it.getColumnIndex(FEEDBACK_COLUMN_CUS_NAME)),
                        it.getString(it.getColumnIndex(FEEDBACK_COLUMN_CUS_EMAIL)),
                        it.getFloat(it.getColumnIndex(FEEDBACK_COLUMN_RATING)),
                        it.getString(it.getColumnIndex(FEEDBACK_COLUMN_COMMENT))
                    )
                    feedbackList.add(feedback)
                }
                cursor.close()
            }
        } finally {
            db.close()
        }

        return feedbackList
    }



    // Delete operation for Feedback by ID
    fun deleteFeedbackById(feedbackId: Int): Int {
        val db = writableDatabase

        return try {
            db.delete(TABLE_FEEDBACK, "$FEEDBACK_COLUMN_ID = ?", arrayOf(feedbackId.toString()))
        } catch (e: SQLException) {
            0 // Return 0 if there's an error
        } finally {
            db.close()
        }
    }












}
