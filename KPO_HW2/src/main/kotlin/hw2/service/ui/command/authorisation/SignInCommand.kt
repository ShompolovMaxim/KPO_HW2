package hw2.service.ui.command.authorisation

import hw2.create.SignedUpWindowBuilderFactory
import hw2.create.SignedUpWindowBuilderFactoryImpl
import hw2.create.WindowBuilder
import hw2.service.ui.command.Command
import hw2.service.ui.window.Window
import hw2.entity.User
import hw2.service.data.UserService

class SignInCommand(private val userService: UserService) : Command {
    override fun run() {
        print("Enter your name: ")
        val name: String = readln()
        print("Enter your password: ")
        val password: String = readln()
        val user: User? = userService.checkUser(name, password)
        if (user == null || name.isEmpty()) {
            println("Incorrect name or password!")
            return
        }
        val signedUpWindowBuilderFactory: SignedUpWindowBuilderFactory = SignedUpWindowBuilderFactoryImpl()
        val signedUpWindowBuilder: WindowBuilder = signedUpWindowBuilderFactory.createBuilder(user)
        val signedUpWindow: Window = signedUpWindowBuilder.create()
        signedUpWindow.run()
    }
}