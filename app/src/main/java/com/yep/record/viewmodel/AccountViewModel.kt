package com.yep.record.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yep.record.helper.FragmentUtil
import com.yep.record.utils.Constant

class AccountViewModel : ViewModel() {

    var loginType: MutableLiveData<Int> = MutableLiveData(Constant.LOGIN_WITH_EMAIL)
    var loginFragment: MutableLiveData<String> = MutableLiveData(FragmentUtil.LOGINFRAGMENT)

}