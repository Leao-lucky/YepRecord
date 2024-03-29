package com.yep.record

import android.app.Application
import android.content.Context


class YepRecordApplication : Application() {

    private val TAG = YepRecordApplication::class.java.simpleName
    companion object {
        lateinit var mContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
    }


    override fun onTerminate() {
        super.onTerminate()
        //程序终止的时候执行
    }
}