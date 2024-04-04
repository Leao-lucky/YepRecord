package com.yep.record.fragment

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yep.record.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var vBinding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        vBinding = FragmentLoginBinding.inflate(layoutInflater)
        return vBinding.root
    }
}