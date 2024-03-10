package hw2.service.ui.command.administrator

import hw2.service.ui.command.Command
import hw2.entity.Dish
import hw2.service.data.MenuService
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class AddDishToMenuCommand(private val menuService: MenuService) : Command {
    override fun run() {
        println("Menu:")
        for(dish in menuService.getAllDishes()) {
            println(dish)
        }
        print("Enter dish name: ")
        val name: String = readln()
        if (menuService.hasDishWithName(name)) {
            println("Dish with such name already exists!")
            return
        }
        try {
            if (name.isEmpty()) {
                throw NumberFormatException()
            }
            print("Enter dish cost: ")
            val cost: Int = readln().toInt()
            if (cost <= 0) {
                throw NumberFormatException()
            }
            print("Enter dish quantity: ")
            val quantity: Int = readln().toInt()
            if (quantity <= 0) {
                throw NumberFormatException()
            }
            print("Enter dish time to cook (seconds): ")
            val timeToCook: Int = readln().toInt()
            if (timeToCook <= 0) {
                throw NumberFormatException()
            }
            menuService.addDish(Dish(name, cost, timeToCook.toDuration(DurationUnit.SECONDS), quantity))
            println("Dish successfully added")
        } catch(ex: NumberFormatException) {
            println("Incorrect value")
            return
        }
    }
}