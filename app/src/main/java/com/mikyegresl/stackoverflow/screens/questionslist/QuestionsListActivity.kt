package com.mikyegresl.stackoverflow.screens.questionslist

import android.os.Bundle
import com.mikyegresl.stackoverflow.R
import com.mikyegresl.stackoverflow.screens.common.activities.BaseActivity

class QuestionsListActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_frame)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.frame_content, QuestionListFragment())
                .commit()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }
}