package com.mikyegresl.stackoverflow

import android.app.Application
import com.mikyegresl.stackoverflow.common.dependency_injection.AppCompositionRoot

class MyApplication: Application() {
    lateinit var appCompositionRoot: AppCompositionRoot

    override fun onCreate() {
        super.onCreate()

        appCompositionRoot = AppCompositionRoot()
    }

}