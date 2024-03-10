package hw2.service.data

import hw2.dao.RevenueDao

class RevenueServiceImpl(private val revenueDao: RevenueDao) : RevenueService {
    override fun getRevenue(): Int {
        return revenueDao.getRevenue()
    }

}