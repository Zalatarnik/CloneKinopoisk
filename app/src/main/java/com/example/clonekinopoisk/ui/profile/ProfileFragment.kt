package com.example.clonekinopoisk.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.clonekinopoisk.R
import com.example.clonekinopoisk.databinding.FragmentProfileBinding
import com.example.clonekinopoisk.ui.fragmentsOfRegistration.LoginFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: Fragment() {

    private var binding: FragmentProfileBinding? =null
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl



            binding?.nameAc?.text = email.toString()

            binding?.buttonDeleteProfile?.setOnClickListener {
                user.delete()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(requireContext(),"User account deleted.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.containerView, LoginFragment())
                    .addToBackStack(null)
                    .commit()

            }

            binding?.buttonSignOut?.setOnClickListener {
                Firebase.auth.signOut()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.containerView, LoginFragment())
                    .addToBackStack(null)
                    .commit()

            }
        }
    }
}