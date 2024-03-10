package hw2.service.ui.command.administrator

import hw2.service.data.MenuService
import hw2.service.ui.command.Command
import hw2.entity.Dish

class EditDishCostCommand(private val menuService: MenuService) : Command {
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
                print("Enter new cost: ")
                val cost: Int = readln().toInt()
                if (cost <= 0) {
                    throw NumberFormatException()
                }
                menuService.editDishCost(name, cost)
                println("Dish successfully edited")
            } catch (ex: NumberFormatException) {
                println("Incorrect value")
            }
        }
    }
}