package com.mikyegresl.stackoverflow.screens.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.mikyegresl.stackoverflow.MyApplication
import com.mikyegresl.stackoverflow.common.dependency_injection.activity.ActivityComponent
import com.mikyegresl.stackoverflow.common.dependency_injection.activity.ActivityModule
import com.mikyegresl.stackoverflow.common.dependency_injection.app.AppComponent
import com.mikyegresl.stackoverflow.common.dependency_injection.presentation.PresentationComponent

open class BaseActivity: AppCompatActivity() {
    private val appComponent: AppComponent get() = (application as MyApplication).appComponent

    val activityComponent: ActivityComponent by lazy {
        appComponent.newActivityComponent(ActivityModule(this))
    }

    protected val presentationComponent: PresentationComponent by lazy {
        activityComponent.newPresentationComponent()
    }
}