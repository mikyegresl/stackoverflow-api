package com.mikyegresl.stackoverflow.screens.common.viewsmvc

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikyegresl.stackoverflow.screens.questiondetails.QuestionDetailsViewMvc
import com.mikyegresl.stackoverflow.screens.questionslist.QuestionListViewMvc

class ViewMvcFactory(private val layoutInflater: LayoutInflater) {

    fun newQuestionsListViewMvc(parent: ViewGroup?): QuestionListViewMvc {
        return QuestionListViewMvc(layoutInflater, parent)
    }

    fun newQuestionDetailsViewMvc(parent: ViewGroup?): QuestionDetailsViewMvc {
        return QuestionDetailsViewMvc(layoutInflater, parent)
    }
}