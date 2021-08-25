package com.techyourchance.dagger2course.screens.common

import android.app.Activity
import android.content.Context
import com.techyourchance.dagger2course.screens.questiondetails.QuestionDetailsActivity

class ScreensNavigator(private val context: Activity) {
    fun toQuestionDetails(questionId : String) {
        QuestionDetailsActivity.start(context, questionId)
    }

    fun navigateBack() {
        context.onBackPressed()
    }
}