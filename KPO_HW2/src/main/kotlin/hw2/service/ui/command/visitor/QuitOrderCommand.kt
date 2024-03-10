package hw2.service.ui.command.visitor

import hw2.service.data.OrderService
import hw2.service.ui.command.Command

class QuitOrderCommand(private val orderService: OrderService) : Command {
    override fun run() {
        try{
            print("Enter order id: ")
            val orderId: Int = readln().toInt()
            if (!orderService.hasOrderWithId(orderId)) {
                println("No order with such id!")
                return
            }
            if (orderService.quitOrder(orderId)) {
                println("Order quit successfully!")
            } else {
                println("Current order status does not allow to quit it!")
            }
        } catch (ex: NumberFormatException) {
            println("Incorrect input!")
        }
    }
}