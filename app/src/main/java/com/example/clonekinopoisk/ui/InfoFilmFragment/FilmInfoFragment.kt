package com.example.clonekinopoisk.ui.InfoFilmFragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.clonekinopoisk.R
import com.example.clonekinopoisk.data.model.Film
import com.example.clonekinopoisk.data.model.PersonInStaff
import com.example.clonekinopoisk.databinding.FragmentFilmInfoBinding
import com.example.clonekinopoisk.ui.adapter.ListFilmsAdapter
import com.example.clonekinopoisk.ui.adapter.PersonInStuffAdapter
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

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


        // button like
        var _like = false
        viewModel.idThis.observe(viewLifecycleOwner) {
            val pathToCheck = "favourite/$userId/favouriteFilms/$it"

            databaseReference.child(pathToCheck).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result.exists()) {
                        binding?.buttonFavourite?.backgroundTintList =
                            ContextCompat.getColorStateList(requireContext(), R.color.red)
                        _like = true
                    } else {
                        binding?.buttonFavourite?.backgroundTintList =
                            ContextCompat.getColorStateList(requireContext(), R.color.not_color)
                        _like = false
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_checking_favorite_movies),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


        // Top image
        viewModel.URLimage.observe(viewLifecycleOwner) { url ->
            if (url.isNullOrEmpty()) {
                viewModel.UrlPoster.observe(viewLifecycleOwner) { urlPoster ->
                    if (urlPoster.isNullOrEmpty()){
                        binding?.mainImageFilm?.run {
                            Glide.with(requireContext())
                                .load("https://avatars.mds.yandex.net/get-kinopoisk-post-img/1374145/09792ccb925715f9b5d85fc22ed445d8/960")
                                .into(this)
                        }
                    }else{
                        binding?.mainImageFilm?.run {
                            Glide.with(requireContext())
                                .load(urlPoster)
                                .into(this)
                        }
                    }

                }
            } else {
                binding?.mainImageFilm?.run {
                    Glide.with(requireContext())
                        .load(url)
                        .into(this)
                }
            }
        }

        val userLanguage = getLanguage()
        when (userLanguage) {
            //If Language = English
            "en" -> {
                // Name film
                viewModel.NameFilmOriginal.observe(viewLifecycleOwner) { nameOr ->
                    if (nameOr.isNullOrEmpty()) {
                        viewModel.NameFilmEnglish.observe(viewLifecycleOwner) { nameEn ->
                            binding?.nameFilm?.text = nameEn.toString()
                        }
                    } else {
                        binding?.nameFilm?.text = nameOr.toString()
                    }
                }
                // title Description
                viewModel.shortDescription.observe(viewLifecycleOwner) {
                    binding?.descriptionShort?.text = it?.toString()?:"Description"
                }
                //  Description
                viewModel.longDescription.observe(viewLifecycleOwner) {
                    binding?.descriptionLong?.text = it?.toString()?:"N/A"
                }

            }

            //If Language = Russian
            "ru" -> {
                // Name film
                viewModel.NameFilmRussen.observe(viewLifecycleOwner) { nameRus ->
                    if (nameRus.isNullOrEmpty()) {
                        viewModel.NameFilmOriginal.observe(viewLifecycleOwner) { nameOr ->
                            binding?.nameFilm?.text = nameOr.toString()
                        }
                    } else {
                        binding?.nameFilm?.text = nameRus.toString()
                    }
                }
                // title Description
                viewModel.shortDescription.observe(viewLifecycleOwner) {
                    binding?.descriptionShort?.text = it?.toString()?:"Описание"
                }
                //  Description
                viewModel.longDescription.observe(viewLifecycleOwner) {
                    binding?.descriptionLong?.text = it?.toString()?:"Нет данных"
                }
            }
            else -> {
            }
        }

        // rating
        viewModel.rating.observe(viewLifecycleOwner) {
            if (it==null) {
                binding?.ratingFilmInfo?.text = " "
            } else {
                binding?.ratingFilmInfo?.visibility = View.VISIBLE
                binding?.ratingFilmInfo?.text = it.toString()
            }
        }

        // year
        viewModel.year.observe(viewLifecycleOwner) {
            binding?.textViewYear?.text = it?.toString()?:"N/A"
        }

        //Genres
        viewModel.listGenres.observe(viewLifecycleOwner) {
                binding?.genres?.text = viewModel.changingGenreString(it.toString())?: "N/A"
        }
        // age limits
        viewModel.ageLimits.observe(viewLifecycleOwner) { age ->
            when (age) {
                "age0" -> {
                    binding?.age?.text = "0+"
                }

                "age6" -> {
                    binding?.age?.text = "6+"
                }

                "age12" -> {
                    binding?.age?.text = "12+"
                }

                "age16" -> {
                    binding?.age?.text = "16+"
                }

                "age18" -> {
                    binding?.age?.text = "18+"
                }

                else -> {
                    binding?.age?.text = age.toString()
                }
            }
        }
        //video play
        viewModel.videForFilm.observe(viewLifecycleOwner) { list ->
            binding?.buttonTreyler?.setOnClickListener {
                val videoUrl = viewModel.getUrlVideosFromList(list)?.toString() ?: "https://www.youtube.com/"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                startActivity(intent)
            }
        }
        viewModel.listRelated.observe(viewLifecycleOwner) {
            if(it.isNullOrEmpty()){
                binding?.notInfoRelated?.visibility = View.VISIBLE
                binding?.containerRelated?.visibility = View.GONE
            }else{
                loadListRelated(it)
            }

        }
        viewModel.listPerson.observe(viewLifecycleOwner) {
            if(it.isNullOrEmpty()){
                binding?.notInfoStaff?.visibility = View.VISIBLE
                binding?.containerStaff?.visibility = View.GONE
            }else{
                loadListStuff(it)
            }
        }

        // add film in favourite
        binding?.buttonFavourite?.setOnClickListener {
            viewModel.ClassForFavourite.observe(viewLifecycleOwner) {
                if (!userId.isNullOrEmpty()) {
                    if (_like == false) {
                        Firebase.database.getReference("favourite")
                            .child(userId)
                            .child("favouriteFilms")
                            .child(it.id)
                            .setValue(it)
                        _like = true
                        binding?.buttonFavourite?.backgroundTintList =
                            ContextCompat.getColorStateList(requireContext(), R.color.red)
                    } else {
                        Firebase.database.getReference("favourite")
                            .child(userId)
                            .child("favouriteFilms")
                            .child(it.id)
                            .removeValue()
                        _like = false
                        binding?.buttonFavourite?.backgroundTintList =
                            ContextCompat.getColorStateList(requireContext(), R.color.not_color)
                    }
                }
            }
        }
        arguments?.getString("id")?.run {
            viewModel.getFilmInfo(this)
        }
    }


    private fun getLanguage(): String {
        val currentLocale: Locale = resources.configuration.locale
        val userLanguage: String = currentLocale.language
        return userLanguage
    }

    private fun loadListRelated(list: List<Film>) {
        binding?.containerRelated?.run {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = ListFilmsAdapter {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.child_container, FilmInfoFragment().apply {
                        arguments = bundleOf("id" to id)
                    })
                    .addToBackStack(null)
                    .commit()
            }
            (adapter as? ListFilmsAdapter)?.submitList(list)
        }
    }

    private fun loadListStuff(list: List<PersonInStaff>) {
        binding?.containerStaff?.run {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = PersonInStuffAdapter()
            (adapter as? PersonInStuffAdapter)?.submitList(list)
        }
    }
}


