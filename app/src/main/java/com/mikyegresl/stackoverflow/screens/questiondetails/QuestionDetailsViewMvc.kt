package com.mikyegresl.stackoverflow.screens.questiondetails

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mikyegresl.stackoverflow.R
import com.mikyegresl.stackoverflow.questions.QuestionWithBody
import com.mikyegresl.stackoverflow.screens.common.image_loader.ImageLoader
import com.mikyegresl.stackoverflow.screens.common.toolbar.MyToolbar
import com.mikyegresl.stackoverflow.screens.common.viewsmvc.BaseViewMvc

class QuestionDetailsViewMvc(
    layoutInflater: LayoutInflater,
    viewGroup: ViewGroup?,
    private val imageLoader: ImageLoader
): BaseViewMvc<QuestionDetailsViewMvc.Listener>(
    layoutInflater,
    viewGroup,
    R.layout.layout_question_details
) {
    interface Listener {
        fun onBackClicked()
    }

    private val toolbar: MyToolbar = findViewById(R.id.toolbar)
    private val swipeRefresh: SwipeRefreshLayout = findViewById(R.id.swipeRefresh)
    private val userImageView: ImageView = findViewById(R.id.user_image)
    private val userNameTextView: TextView = findViewById(R.id.txt_user_name)
    private val txtQuestionBody: TextView = findViewById(R.id.txt_question_body)

    init {
        toolbar.setNavigateUpListener {
            for (listener in listeners) {
                listener.onBackClicked()
            }
        }
        swipeRefresh.isEnabled = false
    }

    fun setQuestionBody(questionBody: QuestionWithBody) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtQuestionBody.text = Html.fromHtml(questionBody.body, Html.FROM_HTML_MODE_LEGACY)
            userNameTextView.text = Html.fromHtml(questionBody.owner.name, Html.FROM_HTML_MODE_LEGACY)
        }
        else {
            @Suppress("DEPRECATION")
            txtQuestionBody.text = Html.fromHtml(questionBody.body)
            @Suppress("DEPRECATION")
            userNameTextView.text = Html.fromHtml(questionBody.owner.name)
        }
        imageLoader.loadImage(questionBody.owner.imageUrl, userImageView)
    }

    fun showProgressIndication() {
        swipeRefresh.isRefreshing = true
    }

    fun hideProgressIndication() {
        swipeRefresh.isRefreshing = false
    }
}