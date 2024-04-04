package com.yep.record.bean

import java.time.LocalDateTime

class UserInfo(pwd: String, em: String) {

    var uid: Long? = null

    var name: String? = ""

    var password: String = pwd

    var email: String = em

    var createTime: LocalDateTime? = null

    var modifiedTime: LocalDateTime? = null

}