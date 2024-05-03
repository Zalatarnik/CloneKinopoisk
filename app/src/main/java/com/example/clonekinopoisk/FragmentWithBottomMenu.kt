package com.example.clonekinopoisk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.clonekinopoisk.databinding.FragmentWithBottomMenuBinding
import com.example.clonekinopoisk.ui.mainFragment.TopFilmsFragment
import com.example.clonekinopoisk.ui.searchFragment.SearchFilmsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentWithBottomMenu: Fragment() {
    private var binding: FragmentWithBottomMenuBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWithBottomMenuBinding.inflate(inflater)
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.findFragmentById(R.id.child_container)?.findNavController()?.let {
            navController ->
            binding?.bottomNavigation?.setupWithNavController(
                navController
            )
        }

    }
}