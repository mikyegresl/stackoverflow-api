package com.mikyegresl.stackoverflow.questions

import com.mikyegresl.stackoverflow.networking.StackoverflowApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.CancellationException
import javax.inject.Inject

class FetchQuestionDetailsUseCase @Inject constructor(private val stackoverflowApi: StackoverflowApi) {
    sealed class Result {
        data class Success(val question: QuestionWithBody): Result()
        object Failure: Result()
    }

    suspend fun fetchQuestionDetails(questionId : String): Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = stackoverflowApi.questionDetails(questionId)

                if (response.isSuccessful && response.body() != null) {
                    return@withContext Result.Success(response.body()!!.question)
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