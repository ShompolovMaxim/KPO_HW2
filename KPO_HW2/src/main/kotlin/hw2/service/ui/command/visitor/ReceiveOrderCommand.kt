package hw2.service.ui.command.visitor

import hw2.service.data.OrderService
import hw2.service.ui.command.Command
import hw2.entity.Order
import hw2.service.data.FeedbackService

class ReceiveOrderCommand(
    private val orderService: OrderService,
    private val feedbackService: FeedbackService
) : Command {
    override fun run() {
        try{
            print("Enter order id: ")
            val orderId: Int = readln().toInt()
            if (!orderService.hasOrderWithId(orderId)) {
                println("No order with such id!")
                return
            }
            if (!orderService.isOrderReady(orderId)) {
                println("Order is not ready!")
                return
            }
            val order: Order = orderService.getOrder(orderId)
            println("Confirm payment for order $${order.getCost()} (enter ${order.getCost()}): ")
            if (readln() == order.getCost().toString()) {
                orderService.payForOrder(orderId)
                println("Order successfully received")
                print("If you want to leave feedback, enter yes: ")
                val response: String = readln()
                if (response != "yes") {
                    return
                }
                for(dish in order.dishes) {
                    println("Feedback on dish ${dish.name}:")
                    print("Enter mark (from 1 to 5): ")
                    val mark: Int = readln().toInt()
                    if (mark < 1 || mark > 5) {
                        throw NumberFormatException("Mark not from 1 to 5")
                    }
                    print("Enter text comment: ")
                    val comment: String = readln()
                    feedbackService.addFeedback(mark, comment, dish.name)
                }
            } else {
                println("Incorrect value, you should have entered ${order.getCost()}")
            }
        } catch (ex: NumberFormatException) {
            println("Incorrect input!")
        }
    }
}