package com.mikyegresl.stackoverflow

import android.app.Application
import com.mikyegresl.stackoverflow.common.dependency_injection.app.AppComponent
import com.mikyegresl.stackoverflow.common.dependency_injection.app.AppModule
import com.mikyegresl.stackoverflow.common.dependency_injection.app.DaggerAppComponent

class MyApplication: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }
}