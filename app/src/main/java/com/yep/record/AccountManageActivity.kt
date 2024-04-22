package com.yep.record

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.yep.record.databinding.ActivityMainBinding
import com.yep.record.helper.FragmentUtil
import com.yep.record.utils.Constant
import com.yep.record.viewmodel.AccountViewModel


class AccountManageActivity : AppCompatActivity() {

    private lateinit var vBinding : ActivityMainBinding

    private val acManageViewModel: AccountViewModel by lazy {
        ViewModelProvider(this).get(AccountViewModel::class.java)
    }

    companion object {
        private const val TAG = "YepRecordActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vBinding.root)
        initView()
    }

    private fun initView() {
        acManageViewModel.loginFragment.observe(this) {
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            if (it == FragmentUtil.LOGINFRAGMENT) {
                //应该要是判断登录缓存，这里为了优先打通主要逻辑先进行简化，后续简化标记为#later
                transaction.add(
                    vBinding.frContainer.id,
                    FragmentUtil.getFragment(FragmentUtil.LOGINFRAGMENT)
                )
            } else if (it == FragmentUtil.REGISTERFRAGMENT){
                transaction.add(
                    vBinding.frContainer.id,
                    FragmentUtil.getFragment(FragmentUtil.REGISTERFRAGMENT)
                )
            }
            transaction.commit()
        }
    }





}