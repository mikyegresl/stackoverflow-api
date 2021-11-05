package com.mikyegresl.stackoverflow.common.service

import android.app.Service
import com.mikyegresl.stackoverflow.MyApplication
import com.mikyegresl.stackoverflow.common.dependency_injection.app.AppComponent
import com.mikyegresl.stackoverflow.common.dependency_injection.service.ServiceComponent
import com.mikyegresl.stackoverflow.common.dependency_injection.service.ServiceModule

abstract class BaseService: Service() {
    private val appComponent: AppComponent get() = (application as MyApplication).appComponent

    val serviceComponent: ServiceComponent by lazy {
        appComponent.newServiceComponent(ServiceModule(this))
    }
}