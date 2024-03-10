package hw2.service.data

import hw2.dao.DishDao
import hw2.dao.OrderDao
import hw2.dao.RevenueDao
import hw2.entity.*
import hw2.exception.OrderNotFoundException
import java.time.Duration
import kotlin.concurrent.thread
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import kotlin.time.toJavaDuration

class OrderServiceImpl(
    private val orderDao: OrderDao,
    private val dishDao: DishDao,
    private val revenueDao: RevenueDao,
    private val user: User
) : OrderService {
    private val orderPriority: MutableMap<Int, Int> = mutableMapOf()
    private var currentProcessingOrderId = -1

    companion object {
        private val lock = Any()
    }

    override fun addDishToOrder(orderId: Int, dishName: String) = synchronized(lock) {
        val dish: Dish = dishDao.getDish(dishName)
        val order: Order = orderDao.getOrder(orderId)
        if (order.visitor.name != user.name) {
            throw OrderNotFoundException("You have no such order!")
        }
        orderDao.deleteOrder(orderId)
        order.addDish(dish)
        orderDao.addOrder(order)
    }

    override fun hasOrderWithId(id: Int): Boolean = synchronized(lock) {
        try {
            val order: Order =  orderDao.getOrder(id)
            return order.visitor.name == user.name
        } catch (ex: OrderNotFoundException) {
            return false
        }
    }

    override fun getAllOrders(): List<Order> = synchronized(lock) {
        val orders: MutableList<Order> = mutableListOf()
        for(orderId in orderDao.getAllOrdersIds()) {
            val order: Order = orderDao.getOrder(orderId)
            if (order.visitor.name == user.name || user.status == UserStatus.ADMINISTRATOR) {
                orders.add(order)
            }
        }
        return orders
    }

    override fun quitOrder(id: Int): Boolean = synchronized(lock) {
        val order: Order = orderDao.getOrder(id)
        if (order.visitor.name != user.name) {
            return false
        }
        if (order.status == OrderStatus.READY || order.status == OrderStatus.PAID) {
            return false
        }
        orderDao.deleteOrder(id)
        order.status = OrderStatus.QUIT
        orderDao.addOrder(order)
        return true
    }

    override fun isOrderReady(id: Int): Boolean = synchronized(lock) {
        try {
            val order: Order = orderDao.getOrder(id)
            return order.status == OrderStatus.READY && order.visitor.name == user.name
        } catch (ex: OrderNotFoundException) {
            return false
        }
    }

    override fun getOrder(id: Int): Order = synchronized(lock) {
        val order: Order = orderDao.getOrder(id)
        if (order.visitor.name != user.name) {
            throw OrderNotFoundException("You have no such order!")
        }
        return order
    }

    override fun payForOrder(id: Int) = synchronized(lock) {
        val order: Order = orderDao.getOrder(id)
        if (order.visitor.name != user.name) {
            throw OrderNotFoundException("You have no such order!")
        }
        orderDao.deleteOrder(id)
        order.status = OrderStatus.PAID
        orderDao.addOrder(order)
        revenueDao.setRevenue(revenueDao.getRevenue() + order.getCost())
    }

    override fun getNewOrder(): Order = synchronized(lock) {
        val orderIds: List<Int> = orderDao.getAllOrdersIds()
        if (orderIds.isEmpty()) {
            return Order(user, 1)
        }
        return Order(user, orderIds.max() + 1)
    }

    override fun addOrder(order: Order){
        order.status = OrderStatus.PREPARING
        orderDao.addOrder(order)
        synchronized(lock) {
            orderPriority[order.id] = order.getCost()
        }
        thread(start = true) {
            cook(order.id)
        }
    }

    override fun getUserNameSpentMostMoney(): String? {
        val orders: List<Order> = getAllOrders()
        if (orders.isEmpty()) {
            return null
        }
        val userMoney: MutableMap<String, Int> = mutableMapOf()
        for (order in orders) {
            if (userMoney.containsKey(order.visitor.name)) {
                userMoney[order.visitor.name] = userMoney[order.visitor.name]!! + order.getCost()
            } else {
                userMoney[order.visitor.name] = order.getCost()
            }
        }
        return userMoney.keys.maxBy { name: String -> userMoney[name]!! }
    }

    override fun getMostOftenBoughtDishName(): String? {
        val orders: List<Order> = getAllOrders()
        if (orders.isEmpty()) {
            return null
        }
        val dishCount: MutableMap<String, Int> = mutableMapOf()
        for (order in orders) {
            for (dish in order.dishes) {
                if (dishCount.containsKey(dish.name)) {
                    dishCount[dish.name] = dishCount[dish.name]!! + 1
                } else {
                    dishCount[dish.name] = 1
                }
            }
        }
        return dishCount.keys.maxBy { name: String -> dishCount[name]!! }
    }

    private fun cook(orderId: Int) {
        try {
            var freeToCook: Boolean = false
            while (!freeToCook) {
                synchronized(lock) {
                    if (currentProcessingOrderId == -1 && orderId == orderPriority.keys.maxBy { id: Int -> orderPriority[id]!! }) {
                        currentProcessingOrderId = orderId
                        freeToCook = true
                    }
                }
            }
            val startTime = System.currentTimeMillis()
            var endTime: Long
            synchronized(lock) {
                endTime = getOrderDuration(orderId).toMillis() + startTime
            }
            while (endTime > System.currentTimeMillis()){
                synchronized(lock){
                    endTime = getOrderDuration(orderId).toMillis() + startTime
                    if (orderDao.getOrder(orderId).status == OrderStatus.QUIT) {
                        return
                    }
                }
            }
            synchronized(lock) {
                val order: Order = orderDao.getOrder(orderId)
                orderDao.deleteOrder(orderId)
                order.status = OrderStatus.READY
                orderDao.addOrder(order)
            }
        } catch (_: OrderNotFoundException) {
            return
        } finally {
            synchronized(lock) {
                currentProcessingOrderId = -1
                orderPriority[orderId] = -1
            }
        }
    }

    private fun getOrderDuration(id: Int) : Duration = synchronized(lock){
        val order: Order = orderDao.getOrder(id)
        var orderDuration: kotlin.time.Duration = 0.toDuration(DurationUnit.SECONDS)
        for(dish in order.dishes) {
            orderDuration += dish.timeToCook
        }
        return orderDuration.toJavaDuration()
    }
}