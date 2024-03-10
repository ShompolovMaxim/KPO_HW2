package hw2.service.ui.command.menu

import hw2.service.ui.command.Command

class ShowVisitorMenuCommand : Command {
    override fun run() {
        println("Enter 1 to make order")
        println("Enter 2 to add dish to order")
        println("Enter 3 to quit order")
        println("Enter 4 to get all orders and their statuses")
        println("Enter 5 to receive order")
        println("Enter 6 to sign out")
    }
}