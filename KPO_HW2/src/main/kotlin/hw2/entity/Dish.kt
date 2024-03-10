package hw2.entity

import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
data class Dish(
    var name: String,
    var cost: Int,
    var timeToCook: Duration,
    var quantity: Int
) {
    override fun toString(): String {
        return "Name: $name, cost: $ $cost, time to cook: ${timeToCook.inWholeSeconds} seconds, quantity: $quantity"
    }
}
