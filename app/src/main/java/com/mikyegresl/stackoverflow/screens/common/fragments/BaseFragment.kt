package com.mikyegresl.stackoverflow.screens.common.fragments

import androidx.fragment.app.Fragment
import com.mikyegresl.stackoverflow.common.dependency_injection.presentation.PresentationComponent
import com.mikyegresl.stackoverflow.screens.common.activities.BaseActivity

open class BaseFragment: Fragment() {
    protected val presentationComponent: PresentationComponent by lazy {
        (requireActivity() as BaseActivity).activityComponent.newPresentationComponent()
    }
}