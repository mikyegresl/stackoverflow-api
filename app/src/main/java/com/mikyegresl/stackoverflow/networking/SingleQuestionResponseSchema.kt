package com.mikyegresl.stackoverflow.networking

import com.google.gson.annotations.SerializedName
import com.mikyegresl.stackoverflow.questions.QuestionWithBody

data class SingleQuestionResponseSchema(@SerializedName("items") val questions: List<QuestionWithBody>) {
    val question: QuestionWithBody get() = questions[0]
}