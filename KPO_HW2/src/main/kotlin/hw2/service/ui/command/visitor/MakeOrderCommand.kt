package hw2.service.ui.command.visitor

import hw2.service.data.MenuService
import hw2.service.data.OrderService
import hw2.service.ui.command.Command
import hw2.entity.Dish
import hw2.entity.Order

class MakeOrderCommand(private val orderService: OrderService, private val menuService: MenuService) : Command {
    override fun run() {
        try {
            println("Menu:")
            for(dish in menuService.getAllDishes()) {
                println(dish)
            }
            print("Enter number of dishes you want to order: ")
            val dishesNumber: Int = readln().toInt()
            if (dishesNumber < 0) {
                throw NumberFormatException("Dishes number less than zero!")
            }
            var i = 0
            println("Enter dishes names one in a line:")
            val dishes: MutableList<Dish> = mutableListOf()
            while (i < dishesNumber) {
                val dishName: String = readln()
                val dish: Dish? = menuService.getDish(dishName)
                if (dish == null) {
                    println("Dish with such name does not exist!")
                    continue
                }
                if (dish.quantity == 0) {
                    println("No such dishes left")
                    continue
                }
                dishes.add(dish)
                menuService.addQuantity(dishName, -1)
                ++i
            }
            val order: Order = orderService.getNewOrder()
            order.dishes = dishes
            orderService.addOrder(order)
            println("Order successfully made!")
        } catch (ex: NumberFormatException) {
            println("Incorrect input!")
        }
    }
}