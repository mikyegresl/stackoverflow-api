package com.techyourchance.dagger2course.screens.questionslist

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.techyourchance.dagger2course.Constants
import com.techyourchance.dagger2course.networking.StackoverflowApi
import com.techyourchance.dagger2course.questions.Question
import com.techyourchance.dagger2course.screens.common.dialogs.ServerErrorDialogFragment
import com.techyourchance.dagger2course.screens.questiondetails.QuestionDetailsActivity
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class QuestionsListActivity : AppCompatActivity(), QuestionListViewMvc.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var stackoverflowApi: StackoverflowApi
    private lateinit var viewMvc: QuestionListViewMvc

    private var isDataLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //init viewMvc
        viewMvc = QuestionListViewMvc(LayoutInflater.from(this), null)
        setContentView(viewMvc.rootView)

        // init retrofit
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        stackoverflowApi = retrofit.create(StackoverflowApi::class.java)
    }

    override fun onStart() {
        super.onStart()

        viewMvc.bindListener(this)

        if (!isDataLoaded) {
            fetchQuestions()
        }
    }

    override fun onStop() {
        super.onStop()

        viewMvc.unbindListener(this)
        coroutineScope.coroutineContext.cancelChildren()
    }

    private fun fetchQuestions() {
        coroutineScope.launch {
            viewMvc.showProgressIndication()

            try {
                val response = stackoverflowApi.lastActiveQuestions(20)

                if (response.isSuccessful && response.body() != null) {
                    viewMvc.bindQuestions(response.body()!!.questions)
                    isDataLoaded = true
                }
                else {
                    onFetchFailed()
                }
            }
            catch (t: Throwable) {
                if (t !is CancellationException) {
                    onFetchFailed()
                }
            }
            finally {
                viewMvc.hideProgressIndication()
            }
        }
    }

    private fun onFetchFailed() {
        supportFragmentManager.beginTransaction()
                .add(ServerErrorDialogFragment.newInstance(), null)
                .commitAllowingStateLoss()
    }

    override fun onRefreshClicked() {
        fetchQuestions()
    }

    override fun onQuestionClicked(clickedQuestion: Question) {
        QuestionDetailsActivity.start(this, clickedQuestion.id)
    }
}