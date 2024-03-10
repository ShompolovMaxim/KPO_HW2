package hw2.dao

import hw2.entity.Dish

interface DishDao {
    fun getDish(name: String) : Dish

    fun addDish(dish: Dish)

    fun deleteDish(name: String)

    fun getAllDishesNames() : List<String>
}