package com.mikyegresl.stackoverflow.common.dependency_injection

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.mikyegresl.stackoverflow.networking.StackoverflowApi
import com.mikyegresl.stackoverflow.screens.common.ScreensNavigator

class ActivityCompositionRoot(private val activity: AppCompatActivity, appCompositionRoot: AppCompositionRoot) {
    val fragmentManager get() = activity.supportFragmentManager

    val layoutInflater get() =  LayoutInflater.from(activity)!!

    val screensNavigator by lazy {
        ScreensNavigator(activity)
    }

    val stackOverflowApi: StackoverflowApi = appCompositionRoot.stackOverflowApi
}