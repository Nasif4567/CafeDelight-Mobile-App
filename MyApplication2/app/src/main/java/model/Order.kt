package model

import DBhelper

/**
 * Order class represents an order model with properties such as orderid, cusid, orderdate, ordertime, and orderstatus.
 */
class Order {
    private var orderid: Int? = null
    private var cusid: Int? = null
    private lateinit var orderdate: String
    private lateinit var ordertime: String
    private lateinit var orderstatus: String

    /**
     * Constructor for creating an Order object with specified properties.
     *
     * @param cusid The customer id associated with the order.
     * @param orderdate The date of the order.
     * @param ordertime The time of the order.
     * @param orderstatus The status of the order.
     */
    constructor(cusid: Int, orderdate: String, ordertime: String, orderstatus: String) {
        this.orderid = orderid
        this.cusid = cusid
        this.orderdate = orderdate
        this.ordertime = ordertime
        this.orderstatus = orderstatus
    }

    /**
     * Constructor for creating an Order object with specified properties including orderId.
     *
     * @param orderid The unique identifier for the order.
     * @param cusid The customer id associated with the order.
     * @param orderdate The date of the order.
     * @param ordertime The time of the order.
     * @param orderstatus The status of the order.
     */
    constructor(orderid: Int, cusid: Int, orderdate: String, ordertime: String, orderstatus: String) {
        this.orderid = orderid
        this.cusid = cusid
        this.orderdate = orderdate
        this.ordertime = ordertime
        this.orderstatus = orderstatus
    }

    // Getter and Setter methods for encapsulation

    fun getOrderId(): Int? {
        return orderid
    }

    fun setOrderId(orderId: Int) {
        orderid = orderId
    }

    fun getOrderCusId(): Int? {
        return cusid
    }

    fun setOrderCusId(cusId: Int) {
        cusid = cusId
    }

    fun getOrderDate(): String {
        return orderdate
    }

    fun setOrderDate(date: String) {
        orderdate = date
    }

    fun getOrderTime(): String {
        return ordertime
    }

    fun setOrderTime(time: String) {
        ordertime = time
    }

    fun getOrderStatus(): String {
        return orderstatus
    }

    fun setOrderStatus(status: String) {
        orderstatus = status
    }
}
