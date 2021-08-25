package com.techyourchance.dagger2course.screens.questiondetails

import android.app.Activity
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.techyourchance.dagger2course.R
import com.techyourchance.dagger2course.screens.common.toolbar.MyToolbar

class QuestionDetailsViewMvc(
    layoutInflater: LayoutInflater,
    viewGroup: ViewGroup?,
    activity: AppCompatActivity
) {
    private val toolbar: MyToolbar
    private val swipeRefresh: SwipeRefreshLayout
    private val txtQuestionBody: TextView

    val rootView : View = layoutInflater.inflate(R.layout.layout_question_details, viewGroup, false)

    init {
        txtQuestionBody = findViewById(R.id.txt_question_body)

        // init toolbar
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigateUpListener { activity.onBackPressed() }

        // init pull-down-to-refresh (used as a progress indicator)
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

    private fun<T: View?> findViewById(@IdRes resourceId: Int): T {
        return rootView.findViewById<T>(resourceId)
    }
}