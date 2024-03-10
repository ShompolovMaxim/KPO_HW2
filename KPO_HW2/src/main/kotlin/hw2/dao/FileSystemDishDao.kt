package hw2.dao

import hw2.exception.DishAlreadyExistsException
import hw2.exception.DishNotFoundException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.apache.commons.io.FileUtils
import hw2.entity.Dish
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import kotlin.io.path.Path

class FileSystemDishDao : DishDao {
    companion object {
        private const val DIR_WITH_DISHES = "dishes"
    }

    override fun getDish(name: String): Dish {
        val file = Path(DIR_WITH_DISHES, name).toFile()

        if (!file.exists()) {
            throw DishNotFoundException("There is no dish with such name")
        }
        val value = FileUtils.readFileToString(file, Charset.defaultCharset())


        return Json.decodeFromString<Dish>(value)
    }

    override fun addDish(dish: Dish) {
        val file = Path(DIR_WITH_DISHES, dish.name).toFile()

        if (file.exists()) {
            throw DishAlreadyExistsException("Dish with such name already exists")
        }

        file.writeText(Json.encodeToString(dish), Charset.defaultCharset())
    }

    override fun deleteDish(name: String) {
        val file = Path(DIR_WITH_DISHES, name).toFile()

        if (!file.exists()) {
            throw DishNotFoundException("There is no dish with such name")
        }

        file.delete()
    }

    override fun getAllDishesNames(): List<String> {
        val files: MutableList<String> = mutableListOf()
        var passedDirectoryName = false
        File(DIR_WITH_DISHES).walk().forEach {
            if (passedDirectoryName) {
                files.add(it.name)
            }
            passedDirectoryName = true
        }
        return files
    }
}