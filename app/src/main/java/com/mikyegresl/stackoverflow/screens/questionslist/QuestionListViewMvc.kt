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

    class QuestionsAdapter(
        private val onQuestionClickListener: (Question) -> Unit
    ) : RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>() {

        private var questionsList: List<Question> = ArrayList(0)

        inner class QuestionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val title: TextView = view.findViewById(R.id.txt_title)
        }

        fun bindData(questions: List<Question>) {
            questionsList = ArrayList(questions)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_question_list_item, parent, false)
            return QuestionViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
            holder.title.text = questionsList[position].title
            holder.itemView.setOnClickListener {
                onQuestionClickListener.invoke(questionsList[position])
            }
        }

        override fun getItemCount(): Int {
            return questionsList.size
        }
    }

    private val swipeRefreshLayout: SwipeRefreshLayout
    private val recyclerView: RecyclerView
    private val questionsAdapter: QuestionsAdapter

    init {
        swipeRefreshLayout = findViewById(R.id.swipeRefresh)
        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)

        questionsAdapter = QuestionsAdapter { clickedQuestion ->
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