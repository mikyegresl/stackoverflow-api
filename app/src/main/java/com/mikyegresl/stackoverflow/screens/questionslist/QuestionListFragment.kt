package com.mikyegresl.stackoverflow.screens.questionslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mikyegresl.stackoverflow.questions.FetchQuestionsUseCase
import com.mikyegresl.stackoverflow.questions.Question
import com.mikyegresl.stackoverflow.screens.common.ScreensNavigator
import com.mikyegresl.stackoverflow.screens.common.dialogs.DialogsNavigator
import com.mikyegresl.stackoverflow.screens.common.fragments.BaseFragment
import kotlinx.coroutines.*

class QuestionListFragment : BaseFragment(), QuestionListViewMvc.Listener {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var viewMvc: QuestionListViewMvc
    private lateinit var fetchQuestionsUseCase: FetchQuestionsUseCase
    private lateinit var dialogsNavigator: DialogsNavigator
    private lateinit var screensNavigator: ScreensNavigator

    private var isDataLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fetchQuestionsUseCase = compositionRoot.fetchQuestionsUseCase
        dialogsNavigator = compositionRoot.dialogNavigator
        screensNavigator = compositionRoot.screensNavigator
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewMvc = compositionRoot.viewMvcFactory.newQuestionsListViewMvc(container)
        return viewMvc.rootView
    }

    override fun onStart() {
        super.onStart()

        viewMvc.bindListeners(this)
        if (!isDataLoaded) {
            fetchQuestions()
        }
    }

    override fun onStop() {
        super.onStop()

        coroutineScope.coroutineContext.cancelChildren()
        viewMvc.unbindListeners(this)
    }

    override fun onRefreshClicked() {
        fetchQuestions()
    }

    override fun onQuestionClicked(clickedQuestion: Question) {
        screensNavigator.toQuestionDetails(clickedQuestion.id)
    }

    private fun onFetchFailed() {
        dialogsNavigator.showServerErrorDialog()
    }

    private fun fetchQuestions() {
        coroutineScope.launch {
            viewMvc.showProgressIndication()

            try {
                val result = fetchQuestionsUseCase.fetchQuestions()

                when (result) {
                    is FetchQuestionsUseCase.Result.Success -> {
                        viewMvc.bindQuestions(result.questions)
                        isDataLoaded = true
                    }
                    is FetchQuestionsUseCase.Result.Failure -> {
                        onFetchFailed()
                    }
                }
            }
            finally {
                viewMvc.hideProgressIndication()
            }
        }
    }
}