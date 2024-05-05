package com.example.clonekinopoisk.ui.searchFragment

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
import com.example.clonekinopoisk.R
import com.example.clonekinopoisk.data.model.FilmWithFilmId
import com.example.clonekinopoisk.databinding.FragmentSearchFilmsBinding
import com.example.clonekinopoisk.ui.adapter.ListFilmsWithFilmIdAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFilmsFragment : Fragment(){

    private var binding: FragmentSearchFilmsBinding? = null
    private val viewModel: SearchFilmsViewModel by viewModels ()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchFilmsBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.searchButton?.setOnClickListener{
            val str = binding?.searchText?.text.toString()
            if (str.isEmpty()){
                binding?.textNotFound?.visibility = View.VISIBLE
            }else{
                binding?.recyclerView?.visibility = View.VISIBLE
                binding?.textNotFound?.visibility = View.GONE
                viewModel.listFound.observe(viewLifecycleOwner){
                    setupRecyclerView(binding?.recyclerView, it)
                }
                viewModel.loadInfo(str,1)
            }
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView?, list: List<FilmWithFilmId>) {
        recyclerView?.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = ListFilmsWithFilmIdAdapter { id ->
                if(!id.isEmpty()){
                    findNavController()
                        .navigate(R.id.action_searchMenu_to_filmInfoFragment2,
                            bundleOf("id" to "301"))

                }
            }
            (adapter as? ListFilmsWithFilmIdAdapter)?.submitList(list)
        }
    }
}