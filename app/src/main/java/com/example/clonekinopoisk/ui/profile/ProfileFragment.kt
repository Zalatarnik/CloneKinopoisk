package com.example.clonekinopoisk.ui.profile

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.clonekinopoisk.R
import com.example.clonekinopoisk.databinding.FragmentProfileBinding
import com.example.clonekinopoisk.domain.SharedPreferencesRepository
import com.example.clonekinopoisk.ui.fragmentsOfRegistration.LoginFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment: Fragment() {

    private var binding: FragmentProfileBinding? = null
    private val viewModel: ProfileViewModel by viewModels()

    private lateinit var imageView: ImageView
    private val PICK_IMAGE = 1

    @Inject
    lateinit var sharedPreferencesRepository: SharedPreferencesRepository

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

        binding?.let { fragmentProfileBinding ->
            imageView = fragmentProfileBinding.avatar

            viewModel.email.observe(viewLifecycleOwner) {
                binding?.nameAc?.text = it
            }


            viewModel.photo.observe(viewLifecycleOwner){
                if (it !=null){
                    binding?.avatar?.let { imageView ->
                        Glide.with(requireContext())
                            .load(it)
                            .into(imageView)
                    }
                }
            }


                binding?.buttonSignOut?.setOnClickListener {
                    Firebase.auth.signOut()
                    sharedPreferencesRepository.logout()
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, LoginFragment())
                        .addToBackStack(null)
                        .commit()
                }

                binding?.buttonDeleteProfile?.setOnClickListener {
                    Firebase.auth.currentUser?.delete()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.deleted_account),
                                    Toast.LENGTH_SHORT
                                ).show()
                                sharedPreferencesRepository.logout()
                            }
                        }
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, LoginFragment())
                        .addToBackStack(null)
                        .commit()
                }

                binding?.buttonAddPhoto?.setOnClickListener {
                    val pickIntent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(pickIntent, PICK_IMAGE)
                }


                viewModel.getInfoUser(Firebase.auth.currentUser)
            }
        }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = data?.data
            imageUri?.let { uri ->
                imageView.setImageURI(uri)

                    // Saving an image on Firebase
                    val profileUpdates = userProfileChangeRequest {
                        photoUri = uri
                    }
                    Firebase.auth.currentUser!!.updateProfile(profileUpdates)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "User profile updated.")
                                viewModel.getInfoUser( Firebase.auth.currentUser)
                            }
                        }
                }
            }
        }
    }
