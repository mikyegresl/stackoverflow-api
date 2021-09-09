package com.mikyegresl.stackoverflow.common.dependency_injection

import com.mikyegresl.stackoverflow.Constants
import com.mikyegresl.stackoverflow.networking.StackoverflowApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppCompositionRoot {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val stackOverflowApi: StackoverflowApi by lazy {
        retrofit.create(StackoverflowApi::class.java)
    }
}