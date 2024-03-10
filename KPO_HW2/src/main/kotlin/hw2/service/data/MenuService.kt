package hw2.service.data

import hw2.entity.Dish
import kotlin.time.Duration

interface MenuService {
    fun hasDishWithName(name: String) : Boolean

    fun addDish(dish: Dish)

    fun deleteDish(name: String)

    fun editDishQuantity(name: String, quantity: Int)

    fun editDishTimeToCook(name: String, timeToCook: Duration)

    fun editDishCost(name: String, cost: Int)

    fun getDish(name: String) : Dish?

    fun getAllDishes() : List<Dish>

    fun addQuantity(name: String, extra: Int)
}