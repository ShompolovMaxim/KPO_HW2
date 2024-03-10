package hw2.dao

import hw2.entity.Feedback

interface FeedbackDao {
    fun getFeedback(id: Int) : Feedback

    fun addFeedback(feedback: Feedback)

    fun deleteFeedback(id: Int)

    fun getAllFeedbackIds() : List<Int>
}