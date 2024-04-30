package com.example.clonekinopoisk.ui.mainFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clonekinopoisk.R
import com.example.clonekinopoisk.data.Film
import com.example.clonekinopoisk.databinding.FragmentTopFilmsBinding
import com.example.clonekinopoisk.ui.InfoFilmFragment.FilmInfoFragment
import com.example.clonekinopoisk.ui.ListFilmsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopFilmsFragment: Fragment() {

    private var binding: FragmentTopFilmsBinding? = null
    private val viewModel: TopFilmsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopFilmsBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.listNewAllTypes.observe(viewLifecycleOwner) {
            setupRecyclerView(binding?.containerNewTop, it)
        }
        viewModel.listTopPopularFilms.observe(viewLifecycleOwner) {
            setupRecyclerView(binding?.containerTopPopularMovies, it)
        }
        viewModel.listTopPopularShows.observe(viewLifecycleOwner) {
            setupRecyclerView(binding?.containerTopPopularShows, it)
        }
        viewModel.listClosesReleases.observe(viewLifecycleOwner) {
            setupRecyclerView(binding?.containerClosesReleases, it)
        }
        viewModel.listFamilyFilms.observe(viewLifecycleOwner) {
            setupRecyclerView(binding?.containerFamily, it)
        }
        viewModel.listLoveTheme.observe(viewLifecycleOwner) {
            setupRecyclerView(binding?.containerLoveTheme, it)
        }
        viewModel.listKidsAnimationTheme.observe(viewLifecycleOwner) {
            setupRecyclerView(binding?.containerKidsAnimation, it)
        }
        viewModel.loadInfo()

    }

    private fun setupRecyclerView(recyclerView: RecyclerView?, list: List<Film>) {
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = ListFilmsAdapter { id ->
                parentFragmentManager.beginTransaction()
                    .replace(R.id.containerView, FilmInfoFragment().apply {
                        arguments = bundleOf("id" to id)
                    })
                    .addToBackStack(null)
                    .commit()
            }
            (adapter as ListFilmsAdapter).submitList(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}


