package com.mikyegresl.stackoverflow.screens.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.mikyegresl.stackoverflow.MyApplication
import com.mikyegresl.stackoverflow.common.dependency_injection.ActivityCompositionRoot
import com.mikyegresl.stackoverflow.common.dependency_injection.PresentationCompositionRoot

open class BaseActivity: AppCompatActivity() {
    private val appCompositionRoot get() = (application as MyApplication).appCompositionRoot

    val activityCompositionRoot by lazy { ActivityCompositionRoot(this, appCompositionRoot) }

    protected val compositionRoot by lazy {
        PresentationCompositionRoot(activityCompositionRoot)
    }
}