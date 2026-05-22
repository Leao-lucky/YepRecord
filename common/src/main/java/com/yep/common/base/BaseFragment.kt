package com.yep.common.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.yep.common.bean.PageData

abstract class BaseFragment<VB: ViewBinding> : Fragment() {

    /** 当前界面 activity 对象*/
    protected lateinit var mContext: Context
    protected lateinit var vBinding: VB
    protected lateinit var pageModel: PageViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        vBinding = getBinding(inflater, container)
        pageModel = ViewModelProvider(requireActivity())[PageViewModel::class.java]
        return vBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 若fragment不需使用findViewById, initView()必须放到这里
        initView()
        initData()
    }

    /** 设置布局layout*/
    //abstract fun setLayoutId(): Int
    abstract fun getBinding(inflater: LayoutInflater, viewGroup: ViewGroup?): VB

    /**
     * 初始化布局
     */
    abstract fun initView()

    /**
     * 初始化数据
     */
    open fun initData() {}

    /**
     * 页面跳转方法
     * sourceId： 跳转来源；Constants.DEFAULT_FOCUS：默认方式跳转，其他：跳转来源页面
     * fragmentId：目的页面Id
     */
    fun start(sourceId: String, fragmentId : String, args: Bundle? = null) {
        val pageData = PageData(sourceId, fragmentId, args)
        pageModel.pageData.value = pageData
    }


    /**
     * 收起键盘
     */
    protected fun hideKeyboard(editText: EditText) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }


}