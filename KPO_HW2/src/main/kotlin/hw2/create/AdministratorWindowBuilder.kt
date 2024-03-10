package hw2.create

import hw2.dao.*
import hw2.service.ui.window.Window
import hw2.entity.User
import hw2.service.data.*
import hw2.service.ui.command.Command
import hw2.service.ui.command.ToPreviousWindowCommand
import hw2.service.ui.command.administrator.*
import hw2.service.ui.command.menu.ShowAdministratorMenuCommand
import hw2.service.ui.window.WindowImpl

class AdministratorWindowBuilder(val user: User) : WindowBuilder {
    override fun create(): Window {
        val dishDao: DishDao = FileSystemDishDao()
        val orderDao: OrderDao = FileSystemOrderDao()
        val revenueDao: RevenueDao = FileSystemRevenueDao()
        val feedbackDao: FeedbackDao = FileSystemFeedbackDao()
        val menuService: MenuService = MenuServiceImpl(dishDao)
        val orderService: OrderService = OrderServiceImpl(orderDao, dishDao, revenueDao, user)
        val feedbackService: FeedbackService = FeedbackServiceImpl(user, feedbackDao, dishDao)
        val revenueService: RevenueService = RevenueServiceImpl(revenueDao)
        val commands: List<Command> = listOf(
            AddDishToMenuCommand(menuService),
            DeleteDishFromMenuCommand(menuService),
            EditDishQuantityCommand(menuService),
            EditDishTimeToCookCommand(menuService),
            EditDishCostCommand(menuService),
            GetMostMoneySpentUserNameCommand(orderService),
            GetMostOftenBoughtDishNameCommand(orderService),
            GetAllDishesMarksCommand(feedbackService),
            GetRevenueCommand(revenueService),
            ToPreviousWindowCommand()
        )
        return WindowImpl(ShowAdministratorMenuCommand(), commands)
    }
}