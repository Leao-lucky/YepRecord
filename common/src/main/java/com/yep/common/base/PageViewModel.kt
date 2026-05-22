package com.yep.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yep.common.bean.PageData

class PageViewModel: ViewModel() {

    val pageData: MutableLiveData<PageData> = MutableLiveData()
}
