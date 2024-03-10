package hw2.service.data

import hw2.entity.User

interface UserService {
    fun checkUser(name: String, password: String): User?

    fun findUser(name: String): User?

    fun addUser(user: User)

    fun signUpUser(name: String, password: String, administratorCode: String) : User?
}