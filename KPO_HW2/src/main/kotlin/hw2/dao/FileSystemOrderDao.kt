package hw2.dao

import hw2.entity.Order
import hw2.exception.OrderAlreadyExistsException
import hw2.exception.OrderNotFoundException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.apache.commons.io.FileUtils
import java.io.File
import java.nio.charset.Charset
import kotlin.io.path.Path

class FileSystemOrderDao : OrderDao {
    companion object {
        private const val DIR_WITH_ORDERS = "src\\main\\resources\\orders"
    }

    override fun getOrder(id: Int): Order {
        val file = Path(DIR_WITH_ORDERS, id.toString()).toFile()

        if (!file.exists()) {
            throw OrderNotFoundException("There is no order with such ID")
        }

        val value = FileUtils.readFileToString(file, Charset.defaultCharset())

        return Json.decodeFromString<Order>(value)
    }

    override fun addOrder(order: Order) {
        val file = Path(DIR_WITH_ORDERS, order.id.toString()).toFile()

        if (file.exists()) {
            throw OrderAlreadyExistsException("Order with such ID already exists")
        }

        file.writeText(Json.encodeToString(order), Charset.defaultCharset())
    }

    override fun deleteOrder(id: Int) {
        val file = Path(DIR_WITH_ORDERS, id.toString()).toFile()

        if (!file.exists()) {
            throw OrderNotFoundException("There is no order with such ID")
        }

        file.delete()
    }

    override fun getAllOrdersIds(): List<Int> {
        val files: MutableList<Int> = mutableListOf()
        var passedDirectoryName = false
        File(DIR_WITH_ORDERS).walk().forEach {
            if (passedDirectoryName) {
                files.add(it.name.toInt())
            }
            passedDirectoryName = true
        }
        return files
    }
}