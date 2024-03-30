package com.yep.record

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yep.record.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var vBinding: ActivityMainBinding

    companion object {
        private const val TAG = "YepRecordActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vBinding.root)
    }




}