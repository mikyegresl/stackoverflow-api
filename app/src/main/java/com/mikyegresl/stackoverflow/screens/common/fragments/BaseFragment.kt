package com.mikyegresl.stackoverflow.screens.common.fragments

import androidx.fragment.app.Fragment
import com.mikyegresl.stackoverflow.common.dependency_injection.PresentationCompositionRoot
import com.mikyegresl.stackoverflow.screens.common.activities.BaseActivity

open class BaseFragment: Fragment() {
    protected val compositionRoot by lazy {
        PresentationCompositionRoot((requireActivity() as BaseActivity).activityCompositionRoot)
    }
}