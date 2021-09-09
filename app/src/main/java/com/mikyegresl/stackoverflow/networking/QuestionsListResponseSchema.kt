package com.mikyegresl.stackoverflow.networking

import com.google.gson.annotations.SerializedName
import com.mikyegresl.stackoverflow.questions.Question

class QuestionsListResponseSchema(@SerializedName("items") val questions: List<Question>)