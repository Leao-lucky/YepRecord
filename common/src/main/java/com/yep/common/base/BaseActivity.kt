package com.yep.common.base

import android.content.Context
import android.os.Bundle
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : RootActivity() {

    protected lateinit var activity : RootActivity
    protected lateinit var context: Context
    protected lateinit var vBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBinding = getViewBinding()
        setContentView(vBinding.root)
        activity = this
        context = this
        initView()
        initData()
    }

    /** binding layout*/
    protected abstract fun getViewBinding(): VB
    /** 初始化view*/
    abstract fun initView()
    /** 接收数据*/
    abstract fun initData()

}