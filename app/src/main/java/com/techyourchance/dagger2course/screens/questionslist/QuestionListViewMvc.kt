package com.techyourchance.dagger2course.screens.questionslist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.techyourchance.dagger2course.R
import com.techyourchance.dagger2course.questions.Question
import java.util.ArrayList

class QuestionListViewMvc(
    layoutInflater: LayoutInflater,
    viewGroup: ViewGroup?
) {
    private val swipeRefreshLayout: SwipeRefreshLayout
    private val recyclerView: RecyclerView
    private val questionsAdapter: QuestionsAdapter

    val rootView: View = layoutInflater.inflate(R.layout.layout_questions_list, viewGroup, false)
    private val context: Context get() = rootView.context
    private val listeners = HashSet<Listener>()

    init {
        // init pull-down-to-refresh
        swipeRefreshLayout = findViewById(R.id.swipeRefresh)

        // init recycler view
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

    interface Listener {
        fun onRefreshClicked()
        fun onQuestionClicked(clickedQuestion: Question)
    }

    fun bindListener(listener: Listener) {
        listeners.add(listener)
    }

    fun unbindListener(listener: Listener) {
        listeners.remove(listener)
    }

    fun showProgressIndication() {
        swipeRefreshLayout.isRefreshing = true
    }

    fun hideProgressIndication() {
        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun <T: View?> findViewById(@IdRes id: Int): T {
        return rootView.findViewById<T>(id)
    }

    fun bindQuestions(questions: List<Question>) {
        questionsAdapter.bindData(questions)
    }
}