package com.mikyegresl.stackoverflow.common.dependency_injection

import com.mikyegresl.stackoverflow.questions.FetchQuestionDetailsUseCase
import com.mikyegresl.stackoverflow.questions.FetchQuestionsUseCase
import com.mikyegresl.stackoverflow.screens.common.dialogs.DialogsNavigator
import com.mikyegresl.stackoverflow.screens.common.viewsmvc.ViewMvcFactory

class PresentationCompositionRoot(private val activityCompositionRoot: ActivityCompositionRoot) {
    private val layoutInflater get() = activityCompositionRoot.layoutInflater
    private val fragmentManager get() = activityCompositionRoot.fragmentManager
    private val stackOverflowApi get() = activityCompositionRoot.stackOverflowApi

    val screensNavigator get() = activityCompositionRoot.screensNavigator
    val viewMvcFactory get() = ViewMvcFactory(layoutInflater)
    val dialogNavigator get() = DialogsNavigator(fragmentManager)
    val fetchQuestionsUseCase get() = FetchQuestionsUseCase(stackOverflowApi)
    val fetchQuestionDetailsUseCase get() = FetchQuestionDetailsUseCase(stackOverflowApi)
}