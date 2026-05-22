package com.yep.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.orhanobut.logger.Logger

/**
 *  项目描述需要有声明周期执行之前的function
 *  暂无任何封装 没什么大作用
 */
open class RootActivity : AppCompatActivity() {

    companion object {
        const val sTAG = "RootActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        onCreateBefore(savedInstanceState)
        super.onCreate(savedInstanceState)
        Logger.d(sTAG, "onCreate $this")
    }

    override fun onStart() {
        onStartBefore()
        super.onStart()
        Logger.d(sTAG, "onStart $this")
    }

    override fun onResume() {
        onResumeBefore()
        super.onResume()
        Logger.d(sTAG, "onResume $this")
    }

    override fun onPause() {
        onPauseBefore()
        super.onPause()
        Logger.d(sTAG, "onPause $this")
    }

    override fun onStop() {
        onStopBefore()
        super.onStop()
        Logger.d(sTAG, "onStop $this")
    }

    override fun onDestroy() {
        onDestroyBefore()
        super.onDestroy()
        Logger.d(sTAG, "onDestroy $this")
    }

    open fun onCreateBefore(savedInstanceState: Bundle?) = Unit

    open fun onStartBefore() = Unit

    open fun onResumeBefore() = Unit

    open fun onPauseBefore() = Unit

    open fun onStopBefore() = Unit

    open fun onDestroyBefore() = Unit
}