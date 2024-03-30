package com.yep.record

import android.app.Application
import com.yep.record.helper.AppManager


class YepRecordApplication : Application() {

    private val TAG = YepRecordApplication::class.java.simpleName

    override fun onCreate() {
        super.onCreate()
        AppManager.register(this)
    }


}