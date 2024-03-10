package hw2.service.ui.command.authorisation

import hw2.create.WindowBuilder
import hw2.create.SignedUpWindowBuilderFactory
import hw2.create.SignedUpWindowBuilderFactoryImpl
import hw2.service.ui.command.Command
import hw2.service.ui.window.Window
import hw2.entity.User
import hw2.service.data.UserService

class SignUpCommand(private val userService: UserService) : Command {
    override fun run() {
        print("Enter your name: ")
        val name: String = readln()
        print("Enter your password: ")
        val password: String = readln()
        print("Enter administrator code if you are one. Otherwise enter anything beside it: ")
        val administratorCode: String = readln()
        val newUser: User? = userService.signUpUser(name, password, administratorCode)
        if (newUser == null || name.isEmpty()) {
            println("Name is already taken!")
            return
        }
        userService.addUser(newUser)
        val windowBuilderFactory: SignedUpWindowBuilderFactory = SignedUpWindowBuilderFactoryImpl()
        val signedUpWindowBuilder: WindowBuilder = windowBuilderFactory.createBuilder(newUser)
        val signedUpWindow: Window = signedUpWindowBuilder.create()
        signedUpWindow.run()
    }
}