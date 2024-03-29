package com.yep.record

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.yep.record.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    protected lateinit var context: Context
    protected lateinit var vBinding: ActivityMainBinding

    companion object {
        private const val TAG = "YepRecordActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        vBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        context = this
        setContentView(vBinding.root)
    }

}