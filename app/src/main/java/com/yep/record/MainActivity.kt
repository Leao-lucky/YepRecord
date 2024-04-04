package com.yep.record

import android.os.Build
import android.os.Bundle
import android.view.SurfaceControl.Transaction
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.yep.record.databinding.ActivityMainBinding
import com.yep.record.helper.FragmentUtil


class MainActivity : AppCompatActivity() {

    private lateinit var vBinding : ActivityMainBinding
    companion object {
        private const val TAG = "YepRecordActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vBinding.root)
        loadRootFragment()
    }

    //应该要是判断登录缓存，这里为了优先打通主要逻辑先进行简化，后续简化标记为#later
    private fun loadRootFragment() {
        val manager = getSupportFragmentManager()
        val transaction = manager.beginTransaction()
        transaction.add(vBinding.frContainer.id, FragmentUtil.getFragment(FragmentUtil.LOGINFRAGMENT))
        transaction.commit()
    }


}