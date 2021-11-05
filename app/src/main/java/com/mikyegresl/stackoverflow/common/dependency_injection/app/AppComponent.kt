package com.mikyegresl.stackoverflow.common.dependency_injection.app

import com.mikyegresl.stackoverflow.common.dependency_injection.activity.ActivityComponent
import com.mikyegresl.stackoverflow.common.dependency_injection.activity.ActivityModule
import com.mikyegresl.stackoverflow.common.dependency_injection.service.ServiceComponent
import com.mikyegresl.stackoverflow.common.dependency_injection.service.ServiceModule
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {
    fun newActivityComponent(activityModule: ActivityModule): ActivityComponent
    fun newServiceComponent(serviceModule: ServiceModule): ServiceComponent
}