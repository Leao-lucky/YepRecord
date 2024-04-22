package com.yep.record.bean

import com.google.gson.annotations.SerializedName

class ResultResponse<T> {
    /**
     * 响应代码
     */
    @SerializedName(value = "code")
    var code: String? = null

    /**
     * 响应消息
     */
    @SerializedName(value = "message")
    var message: String? = null

    /**
     * 响应结果
     */
    @SerializedName(value = "result")
    var result: T? = null

    @Transient
    var requstState: Int = 0
}