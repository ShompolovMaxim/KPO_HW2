package hw2.service.ui.command.visitor

import hw2.service.data.OrderService
import hw2.service.ui.command.Command
import hw2.entity.Order

class GetAllOrdersCommand(private val orderService: OrderService) : Command {
    override fun run() {
        val orders: List<Order> = orderService.getAllOrders()
        if (orders.isEmpty()) {
            println("You have no orders!")
        }
        for (order in orders) {
            println("Order ID: ${order.id}. Order status: ${order.status}. Dishes:")
            for(dish in order.dishes) {
                println("Name: ${dish.name}, cost: $ ${dish.cost}, time to cook: ${dish.timeToCook.inWholeSeconds} seconds")
            }
            println()
        }
    }
}