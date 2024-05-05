package com.example.clonekinopoisk.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clonekinopoisk.ui.adapter.ListFavouriteFilmsAdapter
import com.example.clonekinopoisk.R
import com.example.clonekinopoisk.data.model.FilmFullInfo
import com.example.clonekinopoisk.databinding.FragmentFavouriteBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
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

        viewModel.getUserId()

        viewModel.userId.observe(viewLifecycleOwner){ userId ->
            if (!userId.isNullOrEmpty()) {
                val postReference =  Firebase.database
                    .getReference("favourite")
                    .child(userId)
                    .child("favouriteFilms")

                val postListener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val filmList: List<FilmFullInfo> =
                            dataSnapshot.children.mapNotNull { it.getValue(FilmFullInfo::class.java) }

                        if (filmList.isEmpty()){
                            binding?.NotFavouriteFilms?.visibility = View.VISIBLE
                        }else{
                            setupRecyclerView(binding?.containerFavourite, filmList)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        binding?.NotFavouriteFilms?.visibility = View.VISIBLE
                    }
                }
                postReference.addValueEventListener(postListener)
            }
        }


    }

    private fun setupRecyclerView(recyclerView: RecyclerView?, list: List<FilmFullInfo>?) {
        recyclerView?.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = ListFavouriteFilmsAdapter { id ->
                findNavController()
                    .navigate(R.id.action_favouriteMenu_to_filmInfoFragment2,
                        bundleOf("id" to id))
            }
            (adapter as ListFavouriteFilmsAdapter).submitList(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
