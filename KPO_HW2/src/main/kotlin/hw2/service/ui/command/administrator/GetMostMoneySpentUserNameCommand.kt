package hw2.service.ui.command.administrator

import hw2.entity.Order
import hw2.service.data.OrderService
import hw2.service.ui.command.Command

class GetMostMoneySpentUserNameCommand(private val orderService: OrderService) : Command {
    override fun run() {
        val name: String? = orderService.getUserNameSpentMostMoney()
        if (name == null) {
            println("There are no orders!")
        } else {
            println("User with name $name spent maximal amount of money!")
        }
    }
}