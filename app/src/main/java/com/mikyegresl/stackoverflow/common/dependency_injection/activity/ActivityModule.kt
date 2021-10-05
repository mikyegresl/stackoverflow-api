package com.mikyegresl.stackoverflow.common.dependency_injection.activity

import android.app.Activity
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.mikyegresl.stackoverflow.common.dependency_injection.app.AppComponent
import com.mikyegresl.stackoverflow.screens.common.ScreensNavigator
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(
    private val activity: AppCompatActivity) {
    @Provides
    fun activity() = activity

    @Provides
    fun fragmentManager() = activity.supportFragmentManager

    @Provides
    fun layoutInflater() =  LayoutInflater.from(activity)!!
}