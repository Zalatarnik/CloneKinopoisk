package com.example.clonekinopoisk.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.clonekinopoisk.R
import com.example.clonekinopoisk.domain.SharedPreferencesRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StartFragment: Fragment() {

    @Inject
    lateinit var sharedPreferencesRepository: SharedPreferencesRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return View(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when{
            // first launch of the application
            sharedPreferencesRepository.isFirstLaunch() ->{
                sharedPreferencesRepository.setIsFirstLaunch()
                findNavController().navigate(R.id.action_startFragment_to_onboardingFragment)
            }
            //if you haven't logged in to the ak
            sharedPreferencesRepository.getUserEmail() == null ->{
                findNavController().navigate(R.id.action_startFragment_to_loginFragment2)
            }
            else ->{
                //logging
                findNavController().navigate(R.id.action_startFragment_to_fragmentWithBottomMenu)
            }
        }
    }
}