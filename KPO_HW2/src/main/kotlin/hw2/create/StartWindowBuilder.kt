package hw2.create

import hw2.dao.FileSystemUserDao
import hw2.service.data.UserServiceImpl
import hw2.service.ui.command.*
import hw2.service.ui.command.authorisation.SignInCommand
import hw2.service.ui.command.authorisation.SignUpCommand
import hw2.service.ui.command.menu.ShowStartMenuCommand
import hw2.service.ui.window.Window
import hw2.service.ui.window.WindowImpl
import hw2.service.data.UserService

class StartWindowBuilder : WindowBuilder {
    override fun create()  : Window {
        val userService: UserService = UserServiceImpl(FileSystemUserDao())
        val commands: List<Command> = listOf(SignInCommand(userService), SignUpCommand(userService), ToPreviousWindowCommand())
        return WindowImpl(ShowStartMenuCommand(), commands)
    }
}