package hw2.service.ui.command.administrator

import hw2.service.ui.command.Command
import hw2.service.data.MenuService
import hw2.entity.Dish

class EditDishQuantityCommand(private val menuService: MenuService) : Command {
    override fun run() {
        println("Menu:")
        for(dish in menuService.getAllDishes()) {
            println(dish)
        }
        print("Enter dish name: ")
        val name: String = readln()
        if (!menuService.hasDishWithName(name)) {
            println("No dish with such name!")
        } else {
            try {
                print("Enter new quantity: ")
                val quantity: Int = readln().toInt()
                if (quantity <= 0) {
                    throw NumberFormatException()
                }
                menuService.editDishQuantity(name, quantity)
                println("Dish successfully edited")
            } catch (ex: NumberFormatException) {
                println("Incorrect value")
            }
        }
    }
}