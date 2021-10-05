package com.mikyegresl.stackoverflow.questions

import com.mikyegresl.stackoverflow.networking.StackoverflowApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.CancellationException
import javax.inject.Inject

class FetchQuestionsUseCase @Inject constructor(private val stackoverflowApi: StackoverflowApi) {
    sealed class Result {
        data class Success(val questions: List<Question>) : Result()
        object Failure: Result()
    }

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