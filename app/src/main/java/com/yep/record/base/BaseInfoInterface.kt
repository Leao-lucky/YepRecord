package com.yep.record.base

interface BaseInfoInterface {
    /**
     * 错误码
     * @return
     */
    fun getResultCode(): String?

    /**
     * 错误描述
     * @return
     */
    fun getResultMsg(): String?
}