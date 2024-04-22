package com.yep.record.repository

import com.yep.record.bean.ExceptionEnum
import com.yep.record.bean.ResultResponse
import com.yep.record.bean.UserInfo
import com.yep.record.retrofit.ApiService
import com.yep.record.utils.Constant

class AccountRepository(private val service: ApiService) {

    suspend fun loginByEmail(email: String, verify: String): ResultResponse<UserInfo> {
        var response:ResultResponse<UserInfo>
        try {
            response = service.loginByEmail(email, verify).await()
            if (response.code == ExceptionEnum.SUCCESS.getResultCode()) {
                response.requstState = Constant.REQUEST_SUCCESS
            } else {
                response.requstState = Constant.REQUEST_FAILED
            }
        } catch (e: Exception) {
            response = ResultResponse()
            response.requstState = Constant.REQUEST_EXCEPTION
        }
        return response
    }
}