package com.yep.record.retrofit

import com.yep.record.bean.ResultResponse
import com.yep.record.bean.UserInfo
import kotlinx.coroutines.Deferred
import retrofit2.http.POST


interface ApiService {

    @POST("loginByEmail")
    fun loginByEmail(email: String, verify: String): Deferred<ResultResponse<UserInfo>>
}