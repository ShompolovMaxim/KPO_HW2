package hw2.service.ui.window

import hw2.exception.ToPreviousWindowException
import hw2.service.ui.command.Command

class WindowImpl(
    private val showMenu: Command,
    private val commands: List<Command>
) : Window {
    override fun run() {
        while (true) {
            showMenu.run()
            try {
                val commandNumber: Int = readln().toInt() - 1
                if (commandNumber < 0 || commandNumber > commands.size) {
                    println("Incorrect action number! Try again!")
                    continue
                }
                commands[commandNumber].run()
            } catch (ex: NumberFormatException) {
                println("Incorrect input")
            } catch (ex: ToPreviousWindowException) {
                return
            }
        }
    }
}