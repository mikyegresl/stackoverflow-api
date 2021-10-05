package com.mikyegresl.stackoverflow.screens.common.viewsmvc

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikyegresl.stackoverflow.screens.common.image_loader.ImageLoader
import com.mikyegresl.stackoverflow.screens.questiondetails.QuestionDetailsViewMvc
import com.mikyegresl.stackoverflow.screens.questionslist.QuestionListViewMvc
import javax.inject.Inject

class ViewMvcFactory @Inject constructor(
    private val layoutInflater: LayoutInflater,
    private val imageLoader: ImageLoader
) {
    fun newQuestionsListViewMvc(parent: ViewGroup?): QuestionListViewMvc {
        return QuestionListViewMvc(layoutInflater, parent)
    }

    fun newQuestionDetailsViewMvc(parent: ViewGroup?): QuestionDetailsViewMvc {
        return QuestionDetailsViewMvc(layoutInflater, parent, imageLoader)
    }
}