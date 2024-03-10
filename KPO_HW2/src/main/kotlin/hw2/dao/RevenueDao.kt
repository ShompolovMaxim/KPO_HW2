package hw2.dao

interface RevenueDao {
    fun setRevenue(revenue: Int)

    fun getRevenue() : Int
}