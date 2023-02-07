package com.example.airqualityindex.features.login.controller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.airqualityindex.R
import com.example.airqualityindex.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentLoginBinding.inflate(inflater, container, false)
        this.binding.login = this

        return this.binding.root
    }

    fun onButtonClick(view: View) {
        when (view.id) {
            R.id.btn_login -> {
                if (findNavController().currentDestination?.id == R.id.loginFragment)
                    findNavController().navigate(R.id.action_loginFragment_to_indoorFragment)
            }
            R.id.btn_register -> {
                if (findNavController().currentDestination?.id == R.id.loginFragment)
                    findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }
}