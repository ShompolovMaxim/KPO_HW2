package hw2.service.ui.command.menu

import hw2.service.ui.command.Command

class ShowStartMenuCommand : Command {
    override fun run() {
        println("Enter 1 to sign in")
        println("Enter 2 to sign up")
        println("Enter 3 to exit")
    }
}