package hw2.service.data

import hw2.dao.DishDao
import hw2.exception.DishNotFoundException
import hw2.entity.Dish
import java.io.IOException
import kotlin.time.Duration

class MenuServiceImpl(private val dishDao: DishDao) : MenuService {
    override fun hasDishWithName(name: String): Boolean {
        try {
            dishDao.getDish(name)
            return true
        } catch (ex: DishNotFoundException) {
            return false
        } catch (ex: IOException) {
            return false
        }
    }

    override fun addDish(dish: Dish) {
        dishDao.addDish(dish)
    }

    override fun deleteDish(name: String) {
        dishDao.deleteDish(name)
    }

    override fun editDishQuantity(name: String, quantity: Int) {
        val dish: Dish = dishDao.getDish(name)
        dishDao.deleteDish(name)
        dish.quantity = quantity
        dishDao.addDish(dish)
    }

    override fun editDishTimeToCook(name: String, timeToCook: Duration) {
        val dish: Dish = dishDao.getDish(name)
        dishDao.deleteDish(name)
        dish.timeToCook = timeToCook
        dishDao.addDish(dish)
    }

    override fun editDishCost(name: String, cost: Int) {
        val dish: Dish = dishDao.getDish(name)
        dishDao.deleteDish(name)
        dish.cost = cost
        dishDao.addDish(dish)
    }

    override fun getDish(name: String): Dish? {
        try {
            val dish: Dish = dishDao.getDish(name)
            return dish
        } catch (ex: DishNotFoundException) {
            return null
        } catch (ex: IOException) {
            return null
        }
    }

    override fun getAllDishes(): List<Dish> {
        val dishes: MutableList<Dish> = mutableListOf()
        for(dishName in dishDao.getAllDishesNames()) {
            dishes.add(dishDao.getDish(dishName))
        }
        return dishes
    }

    override fun addQuantity(name: String, extra: Int) {
        editDishQuantity(name, getDish(name)!!.quantity + extra)
    }
}