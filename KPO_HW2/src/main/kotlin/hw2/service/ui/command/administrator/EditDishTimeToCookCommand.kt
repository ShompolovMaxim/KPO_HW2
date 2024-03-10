package hw2.service.ui.command.administrator

import hw2.service.ui.command.Command
import hw2.service.data.MenuService
import hw2.entity.Dish
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class EditDishTimeToCookCommand(private val menuService: MenuService) : Command {
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
                print("Enter new time to cook (seconds): ")
                val timeToCook: Int = readln().toInt()
                if (timeToCook <= 0) {
                    throw NumberFormatException()
                }
                menuService.editDishTimeToCook(name, timeToCook.toDuration(DurationUnit.SECONDS))
                println("Dish successfully edited")
            } catch (ex: NumberFormatException) {
                println("Incorrect value")
            }
        }
    }
}