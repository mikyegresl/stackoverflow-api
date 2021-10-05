package com.mikyegresl.stackoverflow.common.dependency_injection.presentation

import com.mikyegresl.stackoverflow.screens.questiondetails.QuestionDetailsActivity
import com.mikyegresl.stackoverflow.screens.questionslist.QuestionListFragment
import dagger.Subcomponent

@PresentationScope
@Subcomponent
interface PresentationComponent {
    fun inject(fragment: QuestionListFragment)
    fun inject(activity: QuestionDetailsActivity)
}