package com.example.clonekinopoisk.ui.InfoFilmFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.clonekinopoisk.R
import com.example.clonekinopoisk.data.Film
import com.example.clonekinopoisk.data.PersonInStaff
import com.example.clonekinopoisk.databinding.FragmentFilmInfoBinding
import com.example.clonekinopoisk.ui.ListFilmsAdapter
import com.example.clonekinopoisk.ui.PersonInStuffAdapter
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilmInfoFragment: Fragment() {

    private var binding: FragmentFilmInfoBinding? = null
    private val viewModel: FlmInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilmInfoBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = Firebase.auth.currentUser?.uid
        val databaseReference = Firebase.database.reference
        var like = false
        viewModel.idThis.observe(viewLifecycleOwner) {
            val pathToCheck = "favourite/$userId/favouriteFilms/$it"

            databaseReference.child(pathToCheck).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result.exists()) {
                        Toast.makeText(requireContext(), "Child exists!", Toast.LENGTH_SHORT).show()
                        like = true
                    } else {
                        Toast.makeText(requireContext(), "Child does not exist", Toast.LENGTH_SHORT)
                            .show()
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                        like = false

                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Error checking child existence",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


        viewModel.URLimage.observe(viewLifecycleOwner) { url ->
            binding?.mainImageFilm?.run {
                Glide.with(requireContext())
                    .load(url)
                    .into(this)
            }
        }

        viewModel.NameFilmOriginal.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                viewModel.NameFilmRussen.observe(viewLifecycleOwner) { nameRus ->
                    binding?.nameFilm?.text = nameRus.toString()
                }
            } else {
                binding?.nameFilm?.text = it.toString()
            }
        }

        viewModel.rating.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding?.ratingFilmInfo?.text = " "
            } else {
                binding?.ratingFilmInfo?.visibility = View.VISIBLE
                binding?.ratingFilmInfo?.text = it.toString()
            }
        }

        viewModel.year.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding?.textViewYear?.text = "N/A"
            } else {
                binding?.textViewYear?.text = it.toString()
            }
        }

        binding?.buttonFavourite?.setOnClickListener {
            viewModel.ClassForFavourite.observe(viewLifecycleOwner) {


                if (!userId.isNullOrEmpty()) {
                    if (like == false) {
                        Firebase.database.getReference("favourite")
                            .child(userId)
                            .child("favouriteFilms")
                            .child(it.id)
                            .setValue(it)
                        Toast.makeText(requireContext(), "Успешно добавлено", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Firebase.database.getReference("favourite")
                            .child(userId)
                            .child("favouriteFilms")
                            .child(it.id)
                            .removeValue()
                        Toast.makeText(requireContext(), "Успешно удалено", Toast.LENGTH_SHORT)
                            .show()
                    }

                }

            }
        }
        viewModel.shortDescription.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding?.descriptionShort?.text = "N/A"
            } else {
                binding?.descriptionShort?.text = it.toString()
            }
        }
        viewModel.longDescription.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding?.descriptionLong?.text = "N/A"
            } else {
                binding?.descriptionLong?.text = it.toString()
            }
        }
        viewModel.ageLimits.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding?.age?.text = "N/A"
            } else {
                binding?.age?.text = it.toString()
            }
        }

        viewModel.listRelated.observe(viewLifecycleOwner) {
            loadListRelated(it)
        }
//        viewModel.listPerson.observe(viewLifecycleOwner){
//            loadListStuff(it)
//        }


        arguments?.getString("id")?.run {
            viewModel.getFilmInfo(this)
        }
    }

    private fun loadListRelated(list: List<Film>) {
        Toast.makeText(requireContext(), "Лист похожих есть", Toast.LENGTH_SHORT)
        binding?.containerRelated?.run {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = ListFilmsAdapter {
//                parentFragmentManager.beginTransaction()
//                    .replace(R.id.containerView, FilmInfoFragment().apply {
//                        arguments = bundleOf("id" to id)
//                    })
//                    .addToBackStack(null)
//                    .commit()
//            }
                (adapter as ListFilmsAdapter).submitList(list)
            }
        }
    }

        private fun loadListStuff(list: List<PersonInStaff>) {
            if (list.isNullOrEmpty()) {

            } else {
                binding?.containerStaff?.run {
                    layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    adapter = PersonInStuffAdapter()
                    (adapter as PersonInStuffAdapter).submitList(list)
                }
            }
        }

}