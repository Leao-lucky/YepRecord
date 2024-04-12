package com.yep.record.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yep.record.R
import com.yep.record.databinding.FragmentLoginBinding
import com.yep.record.utils.Constant
import com.yep.record.viewmodel.AccountViewModel


class LoginFragment : Fragment() {

    private val vBinding: FragmentLoginBinding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    private val accountViewModel: AccountViewModel by lazy {
        ViewModelProvider(requireActivity()).get(AccountViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        initData()
        return vBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        accountViewModel.getLoginType().observe(viewLifecycleOwner) {
            if (Constant.LOGIN_WITH_EMAIL == it) {
                vBinding.etUserName.hint = resources.getString(R.string.input_email)
                vBinding.etVerify.hint = resources.getString(R.string.input_verify_word)
                vBinding.tvSendVerify.visibility = View.VISIBLE
                vBinding.tvCheckMethod.text = resources.getString(R.string.login_by_username)
            } else if (Constant.LOGIN_WITH_USERNAME == it) {
                vBinding.etUserName.hint = resources.getString(R.string.input_username)
                vBinding.etVerify.hint = resources.getString(R.string.input_pwd)
                vBinding.tvSendVerify.visibility = View.GONE
                vBinding.tvCheckMethod.text = resources.getString(R.string.login_by_email)
            }

        }
    }

    private fun initData() {
        vBinding.tvTips.visibility = View.GONE
        vBinding.tvSendVerify.text = resources.getString(R.string.send_verify, "")
    }

    private fun initListener() {
        vBinding.tvCheckMethod.setOnClickListener {
            accountViewModel.setLoginType()
        }

        vBinding.tvRegister.setOnClickListener {
            accountViewModel.isLoginFragment().value = false
        }

        vBinding.etUserName.doOnTextChanged { text, start, before, count ->
            vBinding.tvTips.visibility = View.GONE
        }

        vBinding.etVerify.doOnTextChanged { text, start, before, count ->
            vBinding.tvTips.visibility = View.GONE
        }

        vBinding.btLogin.setOnClickListener {
            if (vBinding.etUserName.text != null && vBinding.etVerify.text != null) {

            }
        }
    }



}
