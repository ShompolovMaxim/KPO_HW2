package hw2.service.data

import hw2.dao.DishDao
import hw2.dao.FeedbackDao
import hw2.entity.Feedback
import hw2.entity.User

class FeedbackServiceImpl(
    private val user: User,
    private val feedbackDao: FeedbackDao,
    private val dishDao: DishDao
) : FeedbackService {
    override fun addFeedback(mark: Int, comment: String, dishName: String) {
        val feedbackIds: List<Int> = feedbackDao.getAllFeedbackIds()
        var id = 1
        if (feedbackIds.isNotEmpty()) {
            id = feedbackIds.max() + 1
        }
        feedbackDao.addFeedback(Feedback(id ,user,dishDao.getDish(dishName),comment, mark))
    }

    override fun getAllDishesMarks(): MutableMap<String, Float> {
        val dishCount: MutableMap<String, Int> = mutableMapOf()
        val dishSumMarks: MutableMap<String, Int> = mutableMapOf()
        for (id in feedbackDao.getAllFeedbackIds()) {
            val feedback: Feedback = feedbackDao.getFeedback(id)
            if (dishCount.containsKey(feedback.dish.name)) {
                dishCount[feedback.dish.name] = dishCount[feedback.dish.name]!! + 1
                dishSumMarks[feedback.dish.name] = dishSumMarks[feedback.dish.name]!! + feedback.mark
            } else {
                dishCount[feedback.dish.name] = 1
                dishSumMarks[feedback.dish.name] = feedback.mark
            }
        }
        val dishAvgMark: MutableMap<String, Float> = mutableMapOf()
        for (name in dishCount.keys) {
            dishAvgMark[name] = dishSumMarks[name]!!.toFloat() / dishCount[name]!!.toFloat()
        }
        return dishAvgMark
    }
}