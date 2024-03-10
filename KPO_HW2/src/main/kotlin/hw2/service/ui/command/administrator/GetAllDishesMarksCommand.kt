package hw2.service.ui.command.administrator

import hw2.service.data.FeedbackService
import hw2.service.ui.command.Command

class GetAllDishesMarksCommand(private val feedbackService: FeedbackService) : Command {
    override fun run() {
        val dishMark: MutableMap<String, Float> = feedbackService.getAllDishesMarks()
        for (name in dishMark.keys) {
            println("$name: ${dishMark[name]}")
        }
    }
}