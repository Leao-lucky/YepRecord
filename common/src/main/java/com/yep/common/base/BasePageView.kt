package com.yep.common.base

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.yep.common.listener.IPageViewHost

abstract class BasePageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var mPageViewHost: IPageViewHost? = null

    abstract fun onViewHide()

    open fun finish() {
        getHost()?.removePageView(this)
    }

    fun getHost(): IPageViewHost? {
        if (mPageViewHost == null) {
            mPageViewHost = getHostFromParent(this)
        }
        return mPageViewHost
    }


    private fun getHostFromParent(view: View): IPageViewHost? {
        val parent = view.parent
        return if (parent == null) {
            null
        } else {
            if (parent is IPageViewHost) {
                parent
            } else {
                getHostFromParent(parent as View)
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mPageViewHost = null
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        if (visibility != View.VISIBLE) {
            onViewHide()
        }
    }

}