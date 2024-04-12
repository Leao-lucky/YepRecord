package com.yep.record.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yep.record.utils.Constant

class AccountViewModel : ViewModel() {

    private var loginType: MutableLiveData<Int> = MutableLiveData(Constant.LOGIN_WITH_EMAIL)
    private var isLoginFragment: MutableLiveData<Boolean> = MutableLiveData(true)

    fun getLoginType(): MutableLiveData<Int> {
        return loginType
    }

    fun isLoginFragment(): MutableLiveData<Boolean> {
        return isLoginFragment
    }

    fun setLoginType() {
        if (loginType.value == Constant.LOGIN_WITH_EMAIL) {
            loginType.value = Constant.LOGIN_WITH_USERNAME
        } else {
            loginType.value = Constant.LOGIN_WITH_EMAIL
        }
    }

}