package com.mikyegresl.stackoverflow.common.dependency_injection.app

import android.app.Application
import com.mikyegresl.stackoverflow.Constants
import com.mikyegresl.stackoverflow.networking.StackoverflowApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule(private val application: Application) {
    @Provides
    fun application() = application

    @Provides
    @AppScope
    fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @AppScope
    fun stackoverflowApi(retrofit: Retrofit): StackoverflowApi = retrofit.create(StackoverflowApi::class.java)
}