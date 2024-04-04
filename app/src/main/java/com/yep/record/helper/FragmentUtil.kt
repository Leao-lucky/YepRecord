package com.yep.record.helper

import androidx.fragment.app.Fragment
import com.yep.record.fragment.LoginFragment

object FragmentUtil {
    const val SUFFIX = "_fragment"
    const val LOGINFRAGMENT = "login$SUFFIX"

    fun getFragment(key: String): Fragment {
        return when (key) {
            LOGINFRAGMENT -> LoginFragment()
            else -> Fragment()
        }
    }

}