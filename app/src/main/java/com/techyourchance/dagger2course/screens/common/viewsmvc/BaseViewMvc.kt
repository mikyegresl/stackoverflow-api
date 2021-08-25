package com.techyourchance.dagger2course.screens.common.viewsmvc

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes

open class BaseViewMvc<LISTENER_TYPE>(
    layoutInflater: LayoutInflater,
    viewGroup: ViewGroup?,
    @LayoutRes private val layoutResourceId: Int
) {
    protected val listeners = HashSet<LISTENER_TYPE>()
    val rootView : View = layoutInflater.inflate(layoutResourceId, viewGroup, false)
    protected val context: Context get() = rootView.context

    fun bindListeners(listener: LISTENER_TYPE) {
        listeners.add(listener)
    }

    fun unbindListeners(listener: LISTENER_TYPE) {
        listeners.remove(listener)
    }

    protected fun<T: View?> findViewById(@IdRes resourceId: Int): T {
        return rootView.findViewById<T>(resourceId)
    }
}