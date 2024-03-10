package hw2.dao

import hw2.entity.Feedback
import hw2.exception.FeedbackAlreadyExistsException
import hw2.exception.FeedbackNotFoundException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.apache.commons.io.FileUtils
import java.io.File
import java.nio.charset.Charset
import kotlin.io.path.Path

class FileSystemFeedbackDao : FeedbackDao {
    companion object {
        private const val DIR_WITH_FEEDBACK = "feedback"
    }

    override fun getFeedback(id: Int): Feedback {
        val file = Path(DIR_WITH_FEEDBACK, id.toString()).toFile()

        if (!file.exists()) {
            throw FeedbackNotFoundException("There is no feedback with such id")
        }

        val value = FileUtils.readFileToString(file, Charset.defaultCharset())

        return Json.decodeFromString<Feedback>(value)
    }

    override fun addFeedback(feedback: Feedback) {
        val file = Path(DIR_WITH_FEEDBACK, feedback.id.toString()).toFile()

        if (file.exists()) {
            throw FeedbackAlreadyExistsException("Feedback with such id already exists")
        }

        file.writeText(Json.encodeToString(feedback), Charset.defaultCharset())
    }

    override fun deleteFeedback(id: Int) {
        val file = Path(DIR_WITH_FEEDBACK, id.toString()).toFile()

        if (!file.exists()) {
            throw FeedbackNotFoundException("There is no feedback with such id")
        }

        file.delete()
    }

    override fun getAllFeedbackIds(): List<Int> {
        val files: MutableList<Int> = mutableListOf()
        var passedDirectoryName = false
        File(DIR_WITH_FEEDBACK).walk().forEach {
            if (passedDirectoryName) {
                files.add(it.name.toInt())
            }
            passedDirectoryName = true
        }
        return files
    }
}