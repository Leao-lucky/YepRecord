package com.yep.record.helper

import androidx.fragment.app.Fragment
import com.yep.record.fragment.LoginFragment
import com.yep.record.fragment.RegisterFragment

object FragmentUtil {
    const val SUFFIX = "_fragment"
    const val LOGINFRAGMENT = "login$SUFFIX"
    const val REGISTERRAGMENT = "register$SUFFIX"

    fun getFragment(key: String): Fragment {
        return when (key) {

            LOGINFRAGMENT -> LoginFragment()

            REGISTERRAGMENT -> RegisterFragment()

            else -> Fragment()
        }
    }

}