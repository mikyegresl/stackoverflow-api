package com.mikyegresl.stackoverflow.screens.questiondetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mikyegresl.stackoverflow.questions.FetchQuestionDetailsUseCase
import com.mikyegresl.stackoverflow.screens.common.ScreensNavigator
import com.mikyegresl.stackoverflow.screens.common.activities.BaseActivity
import com.mikyegresl.stackoverflow.screens.common.dialogs.DialogsNavigator
import com.mikyegresl.stackoverflow.screens.common.viewsmvc.ViewMvcFactory
import kotlinx.coroutines.*
import javax.inject.Inject

class QuestionDetailsActivity : BaseActivity(), QuestionDetailsViewMvc.Listener {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var viewMvc: QuestionDetailsViewMvc
    private lateinit var questionId: String

    @Inject lateinit var viewMvcFactory: ViewMvcFactory
    @Inject lateinit var fetchQuestionDetailsUseCase: FetchQuestionDetailsUseCase
    @Inject lateinit var screenNavigator: ScreensNavigator
    @Inject lateinit var dialogNavigator: DialogsNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presentationComponent.inject(this)

        viewMvc = viewMvcFactory.newQuestionDetailsViewMvc(null)
        setContentView(viewMvc.rootView)

        questionId = intent.extras!!.getString(EXTRA_QUESTION_ID)!!
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
                        viewMvc.setQuestionBody(result.question)
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