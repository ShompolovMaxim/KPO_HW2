package hw2.dao

import hw2.exception.UserAlreadyExistsException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import hw2.entity.User
import hw2.exception.UserNotFoundException
import org.apache.commons.io.FileUtils
import java.io.File
import java.nio.charset.Charset
import kotlin.io.path.Path

class FileSystemUserDao : UserDao {
    companion object {
        private const val DIR_WITH_USERS = "src\\main\\resources\\users"
    }

    override fun getUser(name: String): User {
        val user = Path(DIR_WITH_USERS, name).toFile()

        if (!user.exists()) {
            throw UserNotFoundException("There is no user with such name")
        }

        val value = FileUtils.readFileToString(user, Charset.defaultCharset())

        return Json.decodeFromString<User>(value)
    }

    override fun addUser(user: User) {
        val file = Path(DIR_WITH_USERS, user.name).toFile()

        if (file.exists()) {
            throw UserAlreadyExistsException("User with such name already exists")
        }

        file.writeText(Json.encodeToString(user), Charset.defaultCharset())
    }

    override fun deleteUser(name: String) {
        val file = Path(DIR_WITH_USERS, name).toFile()

        if (!file.exists()) {
            throw UserNotFoundException("There is no user with such name")
        }

        file.delete()
    }

    override fun getAllUsersNames(): List<String> {
        val files: MutableList<String> = mutableListOf()
        var passedDirectoryName = false
        File(DIR_WITH_USERS).walk().forEach {
            if (passedDirectoryName) {
                files.add(it.name)
            }
            passedDirectoryName = true
        }
        return files
    }
}