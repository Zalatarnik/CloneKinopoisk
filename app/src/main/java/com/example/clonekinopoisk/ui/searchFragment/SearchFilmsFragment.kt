package com.example.clonekinopoisk.ui.searchFragment

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.text.TextWatcher
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clonekinopoisk.R
import com.example.clonekinopoisk.data.model.FilmWithFilmId
import com.example.clonekinopoisk.databinding.FragmentSearchFilmsBinding
import com.example.clonekinopoisk.ui.adapter.ListFilmsWithFilmIdAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFilmsFragment : Fragment() {

    private var binding: FragmentSearchFilmsBinding? = null
    private val viewModel: SearchFilmsViewModel by viewModels()

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

        val savedSearchText = savedInstanceState?.getString("searchText")
        if (!savedSearchText.isNullOrEmpty()) {
            binding?.searchText?.setText(savedSearchText)
            performSearch(savedSearchText)
        }


        binding?.searchText?.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString()
                performSearch(searchText)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("searchText", binding?.searchText?.text.toString())
    }

    private fun performSearch(searchText: String) {
        if (searchText.isEmpty()) {
            binding?.recyclerView?.visibility = View.GONE
        } else {
            binding?.recyclerView?.visibility = View.VISIBLE
            binding?.textNotFound?.visibility = View.GONE
            viewModel.listFound.observe(viewLifecycleOwner) {
                setupRecyclerView(binding?.recyclerView, it)
                if (it.isEmpty()){
                    binding?.textNotFound?.visibility = View.VISIBLE
                    binding?.recyclerView?.visibility = View.GONE
                }
            }
            viewModel.loadInfo(searchText, 1)
        }
    }


        private fun setupRecyclerView(recyclerView: RecyclerView?, list: List<FilmWithFilmId>) {
        recyclerView?.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = ListFilmsWithFilmIdAdapter { id ->
                if(id.isNotEmpty()){
                    findNavController()
                        .navigate(R.id.action_searchMenu_to_filmInfoFragment2,
                            bundleOf("id" to id))

                }
            }
            (adapter as? ListFilmsWithFilmIdAdapter)?.submitList(list)
        }
    }
}
