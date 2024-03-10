package hw2.service.data

interface FeedbackService {
    fun addFeedback(mark: Int, comment: String, dishName: String)

    fun getAllDishesMarks() : MutableMap<String, Float>
}