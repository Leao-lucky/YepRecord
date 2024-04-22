package com.yep.record.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yep.record.bean.ResultResponse
import com.yep.record.bean.UserInfo
import com.yep.record.helper.FragmentUtil
import com.yep.record.repository.AccountRepository
import com.yep.record.retrofit.RetrofitClient
import com.yep.record.utils.Constant
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {

    var loginType: MutableLiveData<Int> = MutableLiveData(Constant.LOGIN_WITH_EMAIL)
    var loginFragment: MutableLiveData<String> = MutableLiveData(FragmentUtil.LOGINFRAGMENT)
    var loginResult: MutableLiveData<ResultResponse<UserInfo>> = MutableLiveData()
    val accountRepository by lazy {
        AccountRepository(RetrofitClient.loginApi)
    }

    fun loginByEmail(email: String, verify: String) {
        viewModelScope.launch {
            loginResult.value = accountRepository.loginByEmail(email, verify)
        }
    }


}