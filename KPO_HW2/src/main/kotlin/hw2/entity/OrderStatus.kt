package hw2.entity

import kotlinx.serialization.Serializable

@Serializable
enum class OrderStatus {
    TAKEN,
    PREPARING,
    READY,
    PAID,
    QUIT
}