package com.mikyegresl.stackoverflow.screens.questiondetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mikyegresl.stackoverflow.questions.FetchQuestionDetailsUseCase
import com.mikyegresl.stackoverflow.screens.common.ScreensNavigator
import com.mikyegresl.stackoverflow.screens.common.activities.BaseActivity
import com.mikyegresl.stackoverflow.screens.common.dialogs.DialogsNavigator
import kotlinx.coroutines.*

class QuestionDetailsActivity : BaseActivity(), QuestionDetailsViewMvc.Listener {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var viewMvc: QuestionDetailsViewMvc
    private lateinit var questionId: String
    private lateinit var fetchQuestionDetailsUseCase: FetchQuestionDetailsUseCase
    private lateinit var screenNavigator: ScreensNavigator
    private lateinit var dialogNavigator: DialogsNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewMvc = compositionRoot.viewMvcFactory.newQuestionDetailsViewMvc(null)
        setContentView(viewMvc.rootView)

        questionId = intent.extras!!.getString(EXTRA_QUESTION_ID)!!

        fetchQuestionDetailsUseCase = compositionRoot.fetchQuestionDetailsUseCase

        screenNavigator = activityCompositionRoot.screensNavigator
        dialogNavigator = compositionRoot.dialogNavigator
    }

    override fun onStart() {
        super.onStart()

        viewMvc.bindListeners(this)
        fetchQuestionDetails()
    }

    override fun onStop() {
        super.onStop()

        viewMvc.unbindListeners(this)
        coroutineScope.coroutineContext.cancelChildren()
    }

    private fun fetchQuestionDetails() {
        coroutineScope.launch {
            viewMvc.showProgressIndication()

            try {
                val result = fetchQuestionDetailsUseCase.fetchQuestionDetails(questionId)

                when (result) {
                    is FetchQuestionDetailsUseCase.Result.Success -> {
                        viewMvc.setQuestionBody(result.question.body)
                    }
                    is FetchQuestionDetailsUseCase.Result.Failure -> {
                        onFetchFailed()
                    }
                }
            }
            finally {
                viewMvc.hideProgressIndication()
            }
        }
    }

    private fun onFetchFailed() {
        dialogNavigator.showServerErrorDialog()
    }

    companion object {
        const val EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID"
        fun start(context: Context, questionId: String) {
            val intent = Intent(context, QuestionDetailsActivity::class.java)
            intent.putExtra(EXTRA_QUESTION_ID, questionId)
            context.startActivity(intent)
        }
    }

    override fun onBackClicked() {
        screenNavigator.navigateBack()
    }
}