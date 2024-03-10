package hw2.service.ui.command.administrator

import hw2.service.data.OrderService
import hw2.service.ui.command.Command

class GetMostOftenBoughtDishNameCommand(private val orderService: OrderService) : Command {
    override fun run() {
        val name: String? = orderService.getMostOftenBoughtDishName()
        if (name == null) {
            println("There are no orders!")
        } else {
            println("Dish with name $name was most often bought!")
        }
    }
}