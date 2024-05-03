package com.example.clonekinopoisk.ui.favourite

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clonekinopoisk.ListFavouriteFilmsAdapter
import com.example.clonekinopoisk.R
import com.example.clonekinopoisk.data.Film
import com.example.clonekinopoisk.data.FilmFullInfo
import com.example.clonekinopoisk.databinding.FragmentFavouriteBinding
import com.example.clonekinopoisk.databinding.FragmentProfileBinding
import com.example.clonekinopoisk.ui.InfoFilmFragment.FilmInfoFragment
import com.example.clonekinopoisk.ui.ListFilmsAdapter
import com.example.clonekinopoisk.ui.profile.ProfileViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment: Fragment() {
    private var binding: FragmentFavouriteBinding? =null
    private val viewModel: FavouriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = Firebase.auth.currentUser?.uid
        if (!userId.isNullOrEmpty()) {
            val postReference =  Firebase.database
                .getReference("favourite")
                .child(userId)
                .child("favouriteFilms")

            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val filmList: List<FilmFullInfo> =
                        dataSnapshot.children.mapNotNull { it.getValue(FilmFullInfo::class.java) }

                    Toast.makeText(requireContext(), "Гуд", Toast.LENGTH_SHORT).show()
                    setupRecyclerView(binding?.containerFavourite, filmList)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "loadPost:onCancelled")
                    Toast.makeText(requireContext(), "Что-то не так", Toast.LENGTH_SHORT).show()
                }
            }
            postReference.addValueEventListener(postListener)
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView?, list: List<FilmFullInfo>?) {
        recyclerView?.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = ListFavouriteFilmsAdapter { id ->
                parentFragmentManager.beginTransaction()
                    .replace(R.id.containerView, FilmInfoFragment().apply {
                        arguments = bundleOf("id" to id)
                    })
                    .addToBackStack(null)
                    .commit()
            }
            (adapter as ListFavouriteFilmsAdapter).submitList(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}