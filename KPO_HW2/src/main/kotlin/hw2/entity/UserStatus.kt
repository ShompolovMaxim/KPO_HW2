package hw2.entity

import kotlinx.serialization.Serializable

@Serializable
enum class UserStatus {
    VISITOR,
    ADMINISTRATOR
}