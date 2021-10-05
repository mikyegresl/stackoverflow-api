package com.mikyegresl.stackoverflow.screens.common

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.mikyegresl.stackoverflow.common.dependency_injection.activity.ActivityScope
import com.mikyegresl.stackoverflow.screens.questiondetails.QuestionDetailsActivity
import javax.inject.Inject

@ActivityScope
class ScreensNavigator @Inject constructor(private val activity: AppCompatActivity) {
    fun toQuestionDetails(questionId : String) {
        QuestionDetailsActivity.start(activity, questionId)
    }

    fun navigateBack() {
        activity.onBackPressed()
    }
}