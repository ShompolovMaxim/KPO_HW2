package hw2.entity

import kotlinx.serialization.Serializable

@Serializable
data class Feedback(
    val id: Int,
    val user: User,
    val dish: Dish,
    var comment: String,
    var mark: Int
)
