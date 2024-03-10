package hw2.dao

import hw2.entity.Order

interface OrderDao {
    fun getOrder(id: Int) : Order

    fun addOrder(order: Order)

    fun deleteOrder(id: Int)

    fun getAllOrdersIds() : List<Int>
}