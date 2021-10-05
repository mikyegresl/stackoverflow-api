package com.mikyegresl.stackoverflow.screens.questionslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mikyegresl.stackoverflow.R
import com.mikyegresl.stackoverflow.questions.Question
import com.mikyegresl.stackoverflow.screens.common.viewsmvc.BaseViewMvc
import java.util.ArrayList

class QuestionListViewMvc(
    layoutInflater: LayoutInflater,
    viewGroup: ViewGroup?
): BaseViewMvc<QuestionListViewMvc.Listener>(
    layoutInflater,
    viewGroup,
    R.layout.layout_questions_list
) {
    interface Listener {
        fun onRefreshClicked()
        fun onQuestionClicked(clickedQuestion: Question)
    }

    private val swipeRefreshLayout: SwipeRefreshLayout = findViewById(R.id.swipeRefresh)
    private val recyclerView: RecyclerView = findViewById(R.id.recycler)
    private val questionsAdapter: QuestionListAdapter

    init {
        recyclerView.layoutManager = LinearLayoutManager(context)

        questionsAdapter = QuestionListAdapter { clickedQuestion ->
            for (listener in listeners) {
                listener.onQuestionClicked(clickedQuestion)
            }
        }
        recyclerView.adapter = questionsAdapter

        swipeRefreshLayout.setOnRefreshListener {
            for (listener in listeners) {
                listener.onRefreshClicked()
            }
        }
    }

    fun showProgressIndication() {
        swipeRefreshLayout.isRefreshing = true
    }

    fun hideProgressIndication() {
        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = false
        }
    }

    fun bindQuestions(questions: List<Question>) {
        questionsAdapter.bindData(questions)
    }
}