package com.example.airqualityindex.drawerFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.airqualityindex.R
import com.example.airqualityindex.databinding.FragmentChangeLocationBinding
import com.example.airqualityindex.databinding.FragmentMyAirBinding
import com.example.airqualityindex.workers.NavigationCallbackImpl
import org.koin.android.ext.android.get

class MyAirFragment : Fragment() {

    private val navCallback: NavigationCallbackImpl = get()

    private lateinit var binding: FragmentMyAirBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentMyAirBinding.inflate(inflater, container, false)

        this.navCallback.navigationCallback?.onInvisibleBottomNavigation()
        this.binding.openCustomDrawer.setOnClickListener { this.navCallback.navigationCallback?.onPressBack() }

        return this.binding.root
    }
}