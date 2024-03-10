package hw2.service.data

import hw2.dao.UserDao
import hw2.entity.User
import hw2.entity.UserStatus
import hw2.exception.UserNotFoundException
import java.io.IOException
import java.util.*

class UserServiceImpl(private val userDao: UserDao) : UserService {
    override fun checkUser(name: String, password: String): User? {
        return try {
            val user = userDao.getUser(name)
            if (user.password == password) {
                user
            } else {
                null
            }
        } catch (ex: UserNotFoundException) {
            null
        }  catch (ex: IOException) {
            null
        }
    }

    override fun findUser(name: String): User? {
        return try {
            val user = userDao.getUser(name)
            user
        } catch (ex: UserNotFoundException) {
            null
        }  catch (ex: IOException) {
            null
        }
    }

    override fun addUser(user: User) {
        userDao.addUser(user)
    }

    override fun signUpUser(name: String, password: String, administratorCode: String): User? {
        val user: User? = findUser(name)
        if (user != null) {
            return null
        }
        val calendar: Calendar = Calendar.getInstance()
        return if (administratorCode == calendar.get(Calendar.DAY_OF_MONTH).toString()) {
            User(name, password, UserStatus.ADMINISTRATOR)
        } else {
            User(name, password, UserStatus.VISITOR)
        }
    }
}