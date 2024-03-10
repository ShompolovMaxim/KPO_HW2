package hw2.dao

import org.apache.commons.io.FileUtils
import java.nio.charset.Charset
import kotlin.io.path.Path

class FileSystemRevenueDao : RevenueDao{
    companion object {
        private const val DIR_WITH_REVENUE = "src\\main\\resources\\revenue"
    }

    override fun setRevenue(revenue: Int) {
        val file = Path(DIR_WITH_REVENUE, "revenue").toFile()

        file.writeText(revenue.toString(), Charset.defaultCharset())
    }

    override fun getRevenue(): Int {
        val file = Path(DIR_WITH_REVENUE, "revenue").toFile()

        val value = FileUtils.readFileToString(file, Charset.defaultCharset())

        return value.toInt()
    }
}