package com.yep.record.helper

import androidx.fragment.app.Fragment
import com.yep.record.fragment.LoginFragment
import com.yep.record.fragment.RegisterFragment
import com.yep.record.utils.Constant

object FragmentUtil {
    const val SUFFIX = "_fragment"
    const val LOGINFRAGMENT = "login$SUFFIX"
    const val REGISTERFRAGMENT = "register$SUFFIX"
    const val MAINFFRAGMENT = "main$SUFFIX"

    fun getFragment(key: String): Fragment {
        return when (key) {

            LOGINFRAGMENT -> LoginFragment()

            REGISTERFRAGMENT -> RegisterFragment()

            else -> Fragment()
        }
    }

}