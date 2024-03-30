package com.yep.record

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.yep.record.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var context: Context
    private lateinit var vBinding: ActivityMainBinding

    companion object {
        private const val TAG = "YepRecordActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        vBinding = ActivityMainBinding.inflate(layoutInflater)
        context = this
        setContentView(vBinding.root)
    }

}