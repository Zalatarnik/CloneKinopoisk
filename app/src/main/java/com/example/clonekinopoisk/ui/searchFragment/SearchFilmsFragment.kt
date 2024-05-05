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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clonekinopoisk.R
import com.example.clonekinopoisk.data.Film
import com.example.clonekinopoisk.databinding.FragmentSearchFilmsBinding
import com.example.clonekinopoisk.ui.InfoFilmFragment.FilmInfoFragment
import com.example.clonekinopoisk.ui.ListFilmsAdapter
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
            var str = binding?.searchText?.text.toString()
            //var keyword = viewModel.urlEncodeString(str.toString())

            if (str.isEmpty()){
                binding?.textNotFound?.visibility = View.VISIBLE
            }else{
                viewModel.listFound.observe(viewLifecycleOwner){
                    setupRecyclerView(binding?.recyclerView, it)
                }
                viewModel.loadInfo(str,1)
                binding?.recyclerView?.visibility = View.VISIBLE
            }
        }

    }


    private fun setupRecyclerView(recyclerView: RecyclerView?, list: List<Film>?) {
        recyclerView?.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = ListFilmsAdapter { id ->
                 findNavController()
                    .navigate(R.id.action_searchMenu_to_filmInfoFragment2,
                        bundleOf("id" to id))
            }
            (adapter as ListFilmsAdapter).submitList(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}