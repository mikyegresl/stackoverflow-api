package com.mikyegresl.stackoverflow.screens.common

import android.app.Activity
import com.mikyegresl.stackoverflow.screens.questiondetails.QuestionDetailsActivity

class ScreensNavigator(private val context: Activity) {
    fun toQuestionDetails(questionId : String) {
        QuestionDetailsActivity.start(context, questionId)
    }

    fun navigateBack() {
        context.onBackPressed()
    }
}