package com.techyourchance.dagger2course.questions

import com.techyourchance.dagger2course.Constants
import com.techyourchance.dagger2course.networking.StackoverflowApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.CancellationException

class FetchQuestionsUseCase {
    sealed class Result {
        class Success(val questions: List<Question>) : Result()
        object Failure: Result()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val stackoverflowApi = retrofit.create(StackoverflowApi::class.java)

    suspend fun fetchQuestions(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = stackoverflowApi.lastActiveQuestions(20)

                if (response.isSuccessful && response.body() != null) {
                    return@withContext Result.Success(response.body()!!.questions)
                }
                else {
                    return@withContext Result.Failure
                }
            }
            catch (t: Throwable) {
                if (t !is CancellationException) {
                    return@withContext Result.Failure
                }
                else {
                    throw t
                }
            }
        }
    }
}