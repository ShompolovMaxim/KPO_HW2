package hw2.service.ui.command.visitor

import hw2.entity.Order
import hw2.entity.OrderStatus
import hw2.service.ui.command.Command
import hw2.service.data.MenuService
import hw2.service.data.OrderService

class AddDishToOrderCommand(private val orderService: OrderService, private val menuService: MenuService) : Command {
    override fun run() {
        try{
            print("Enter order id: ")
            val orderId: Int = readln().toInt()
            if (!orderService.hasOrderWithId(orderId)) {
                println("No order with such id!")
                return
            }
            val order: Order = orderService.getOrder(orderId)
            if (order.status != OrderStatus.PREPARING && order.status != OrderStatus.TAKEN) {
                println("Order is already finished! You cannot add dish!")
                return
            }
            println("Order ID: ${order.id}. Order status: ${order.status}. Dishes:")
            for(dish in order.dishes) {
                println("Name: ${dish.name}, cost: $ ${dish.cost}, time to cook: ${dish.timeToCook.inWholeSeconds} seconds")
            }
            println("Menu:")
            for(dish in menuService.getAllDishes()) {
                println(dish)
            }
            println("Order ID: ${order.id}. Order status: ${order.status}. Dishes:")
            for(dish in order.dishes) {
                println("Name: ${dish.name}, cost: $ ${dish.cost}, time to cook: ${dish.timeToCook.inWholeSeconds} seconds")
            }
            print("Enter dish name: ")
            val dishName: String = readln()
            if (!menuService.hasDishWithName(dishName)) {
                println("No dish with such name!")
                return
            }
            if (menuService.getDish(dishName)!!.quantity == 0) {
                println("No such dishes left!")
                return
            }
            if (order.status != OrderStatus.PREPARING && order.status != OrderStatus.TAKEN) {
                println("Order is already finished! You cannot add dish!")
                return
            }
            menuService.addQuantity(dishName, -1)
            orderService.addDishToOrder(orderId, dishName)
        } catch (ex: NumberFormatException) {
            println("Incorrect input!")
        }
    }
}