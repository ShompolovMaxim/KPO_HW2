package hw2.service.ui.command.menu

import hw2.service.ui.command.Command

class ShowAdministratorMenuCommand : Command {
    override fun run() {
        println("Enter 1 to add dish")
        println("Enter 2 to delete dish")
        println("Enter 3 to edit dish quantity")
        println("Enter 4 to edit dish cooking time")
        println("Enter 5 to edit dish cost")
        println("Enter 6 to show user who spent maximal amount of money")
        println("Enter 7 to show most often bought dish")
        println("Enter 8 to show all dishes average marks")
        println("Enter 9 to show revenue")
        println("Enter 10 to sign out")
    }
}