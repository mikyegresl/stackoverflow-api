package com.mikyegresl.stackoverflow.common.dependency_injection.app

import com.mikyegresl.stackoverflow.common.dependency_injection.activity.ActivityComponent
import com.mikyegresl.stackoverflow.common.dependency_injection.activity.ActivityModule
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {
    fun newActivityComponent(activityModule: ActivityModule): ActivityComponent
}