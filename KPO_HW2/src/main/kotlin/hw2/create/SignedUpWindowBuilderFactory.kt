package hw2.create

import hw2.entity.User

interface SignedUpWindowBuilderFactory {
    fun createBuilder(user: User) : WindowBuilder
}