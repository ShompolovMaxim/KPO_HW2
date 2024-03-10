package hw2.dao

import hw2.entity.User

interface UserDao {
    fun getUser(name: String) : User

    fun addUser(user: User)

    fun deleteUser(name: String)

    fun getAllUsersNames() : List<String>
}