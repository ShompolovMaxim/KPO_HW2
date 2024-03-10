package hw2.entity

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var name: String,
    var password: String,
    var status: UserStatus
)
