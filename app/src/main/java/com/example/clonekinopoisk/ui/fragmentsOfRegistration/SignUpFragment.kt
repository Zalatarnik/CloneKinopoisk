package com.example.clonekinopoisk.ui.fragmentsOfRegistration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.clonekinopoisk.R
import com.example.clonekinopoisk.databinding.FragmentSignUpBinding
import com.example.clonekinopoisk.ui.mainFragment.TopFilmsFragment
import com.example.clonekinopoisk.ui.profile.ProfileFragment
import com.example.clonekinopoisk.ui.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(){

    private var binding: FragmentSignUpBinding? = null
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater)
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
            viewModel.registerUser(email, password)
        }


        viewModel.registrationState.observe(viewLifecycleOwner) { state ->
            when(state){
                RegistrationState.Error("Что-то пошло не так") ->{
                    Toast.makeText(requireContext(),"Что-то пошло не так", Toast.LENGTH_SHORT).show()
                }
                RegistrationState.Loading -> {

                }
                RegistrationState.Success -> {
                    Toast.makeText(requireContext(),"Успешно", Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }



        viewModel.navigationEvent.observe(viewLifecycleOwner) {even ->
            if(even == NavigationEvent.NavigateToMainFragment) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.containerView, TopFilmsFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

        binding?.buttonSwitching?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.containerView, LoginFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}