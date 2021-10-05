package com.mikyegresl.stackoverflow.common.dependency_injection.activity

import com.mikyegresl.stackoverflow.common.dependency_injection.presentation.PresentationComponent
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun newPresentationComponent(): PresentationComponent
}