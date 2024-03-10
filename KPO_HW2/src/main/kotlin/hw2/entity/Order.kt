package hw2.entity

import kotlinx.serialization.Serializable

@Serializable
data class Order(val visitor: User, val id: Int) {
    @Serializable
    var dishes: MutableList<Dish> = mutableListOf()
    var status: OrderStatus = OrderStatus.TAKEN

    fun addDish(dish: Dish) {
        dishes.add(dish)
    }

    fun getCost() : Int {
        var cost = 0
        for(dish in dishes) {
            cost += dish.cost
        }
        return cost
    }
}
