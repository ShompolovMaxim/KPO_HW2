package hw2.service.ui.command.administrator

import hw2.service.ui.command.Command
import hw2.service.data.MenuService

class DeleteDishFromMenuCommand(private val menuService: MenuService) : Command {
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
            menuService.deleteDish(name)
            println("Dish deleted successfully")
        }
    }
}