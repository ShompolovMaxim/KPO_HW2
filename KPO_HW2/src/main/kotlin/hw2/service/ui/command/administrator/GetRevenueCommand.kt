package hw2.service.ui.command.administrator

import hw2.dao.RevenueDao
import hw2.service.data.RevenueService
import hw2.service.ui.command.Command

class GetRevenueCommand(private val revenueService: RevenueService) : Command {
    override fun run() {
        println("Revenue: $ ${revenueService.getRevenue()}")
    }
}