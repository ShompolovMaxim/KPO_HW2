package hw2.create

import hw2.dao.*
import hw2.service.ui.window.Window
import hw2.entity.User
import hw2.service.data.*
import hw2.service.ui.command.Command
import hw2.service.ui.command.ToPreviousWindowCommand
import hw2.service.ui.window.WindowImpl
import hw2.service.ui.command.menu.ShowVisitorMenuCommand
import hw2.service.ui.command.visitor.*

class VisitorWindowBuilder(val user: User) : WindowBuilder {
    override fun create(): Window {
        val dishDao: DishDao = FileSystemDishDao()
        val orderDao: OrderDao = FileSystemOrderDao()
        val revenueDao: RevenueDao = FileSystemRevenueDao()
        val feedbackDao: FeedbackDao = FileSystemFeedbackDao()
        val menuService: MenuService = MenuServiceImpl(dishDao)
        val feedbackService: FeedbackService = FeedbackServiceImpl(user, feedbackDao, dishDao)
        val orderService: OrderService = OrderServiceImpl(orderDao, dishDao, revenueDao, user)
        val commands: List<Command> = listOf(
            MakeOrderCommand(orderService, menuService),
            AddDishToOrderCommand(orderService, menuService),
            QuitOrderCommand(orderService),
            GetAllOrdersCommand(orderService),
            ReceiveOrderCommand(orderService, feedbackService),
            ToPreviousWindowCommand()
        )
        return WindowImpl(ShowVisitorMenuCommand(), commands)
    }
}