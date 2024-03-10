package hw2.service.data

import hw2.entity.Order

interface OrderService {
    fun addDishToOrder(orderId: Int, dishName: String)

    fun hasOrderWithId(id: Int) : Boolean

    fun getAllOrders() : List<Order>

    fun quitOrder(id: Int): Boolean

    fun isOrderReady(id: Int) : Boolean

    fun getOrder(id: Int) : Order

    fun payForOrder(id: Int)

    fun getNewOrder() : Order

    fun addOrder(order: Order)

    fun getUserNameSpentMostMoney() : String?

    fun getMostOftenBoughtDishName() : String?
}