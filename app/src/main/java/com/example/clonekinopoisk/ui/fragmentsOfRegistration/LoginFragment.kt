package com.example.clonekinopoisk.ui.fragmentsOfRegistration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.clonekinopoisk.R
import com.example.clonekinopoisk.databinding.FragmentLoginBinding
import com.example.clonekinopoisk.ui.InfoFilmFragment.FilmInfoFragment
import com.example.clonekinopoisk.ui.favourite.FavouriteFragment
import com.example.clonekinopoisk.ui.mainFragment.TopFilmsFragment
import com.example.clonekinopoisk.ui.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: Fragment() {

    private var binding: FragmentLoginBinding?= null
    private  val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.emailError.observe(viewLifecycleOwner) { error ->
            binding?.editEmailAddress?.error = error
        }
        viewModel.passwordError.observe(viewLifecycleOwner) { error ->
            binding?.editPassword?.error = error
        }

        binding?.signUpButton?.setOnClickListener {
            val email = binding?.editEmailAddress?.text.toString()
            val password = binding?.editPassword?.text.toString()
            viewModel.login(email, password)
        }


        viewModel.registrationState.observe(viewLifecycleOwner) { state ->
            when(state){
                RegistrationState.Error("@strings/something_went_wrong") ->{
                    Toast.makeText(requireContext(),getText(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
                }
                RegistrationState.Loading -> {

                }
                RegistrationState.Success -> {
                    Toast.makeText(requireContext(),getText(R.string.successfully), Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        viewModel.navigationEvent.observe(viewLifecycleOwner) {even ->
            if(even == NavigationEvent.NavigateToMainFragment) {
               findNavController().navigate(R.id.action_loginFragment2_to_fragmentWithBottomMenu)
            }
        }

        binding?.buttonSwitching?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment2_to_signUpFragment3)

        }
    }
}