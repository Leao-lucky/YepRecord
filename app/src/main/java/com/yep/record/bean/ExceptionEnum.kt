package com.yep.record.bean

enum class ExceptionEnum(
    val resultCode: String,
    val resultMsg: String
) {
    SUCCESS("200", "成功!"),
    BODY_NOT_MATCH("400", "请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH("401", "请求的数字签名不匹配!"),
    NOT_FOUND("404", "未找到该资源!"),
    EMAIL_EXIST("405", "邮箱已存在"),
    PASSWORD_NOT_MATCH("406", "验证失败!"),
    INFORMATION_NOT_FOUND("407", "用户名或邮箱未找到!"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误!"),
    SERVER_BUSY("503", "服务器正忙，请稍后再试!"),
    EMAIL_SEND_ERROR("504", "邮件发送错误");

}