package com.mikyegresl.stackoverflow.screens.questiondetails

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mikyegresl.stackoverflow.R
import com.mikyegresl.stackoverflow.screens.common.toolbar.MyToolbar
import com.mikyegresl.stackoverflow.screens.common.viewsmvc.BaseViewMvc

class QuestionDetailsViewMvc(
    layoutInflater: LayoutInflater,
    viewGroup: ViewGroup?
): BaseViewMvc<QuestionDetailsViewMvc.Listener>(
    layoutInflater,
    viewGroup,
    R.layout.layout_question_details
) {
    interface Listener {
        fun onBackClicked()
    }

    private val toolbar: MyToolbar = findViewById(R.id.toolbar)
    private val swipeRefresh: SwipeRefreshLayout
    private val txtQuestionBody: TextView = findViewById(R.id.txt_question_body)

    init {

        toolbar.setNavigateUpListener {
            for (listener in listeners) {
                listener.onBackClicked()
            }
        }

        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.isEnabled = false
    }

    fun setQuestionBody(questionBody: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtQuestionBody.text = Html.fromHtml(questionBody, Html.FROM_HTML_MODE_LEGACY)
        }
        else {
            @Suppress("DEPRECATION")
            txtQuestionBody.text = Html.fromHtml(questionBody)
        }
    }

    fun showProgressIndication() {
        swipeRefresh.isRefreshing = true
    }

    fun hideProgressIndication() {
        swipeRefresh.isRefreshing = false
    }
}