package com.example.airqualityindex.workers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.airqualityindex.R
import com.example.airqualityindex.databinding.FragmentRegisterBinding
import com.example.airqualityindex.units.RegisterVerify

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentRegisterBinding.inflate(inflater, container, false)
        this.binding.register = this

        return this.binding.root
    }

    fun onButtonClick(view: View) {
        when (view.id) {
            R.id.btn_register -> {
                if (findNavController().currentDestination?.id == R.id.registerFragment) {
                    if (RegisterVerify().isLoginIdVerify(binding.editAccount.text.toString()))
                        findNavController().navigateUp()
                }
            }
        }
    }


}