package com.example.clonekinopoisk.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clonekinopoisk.data.model.FilmWithFilmId
import com.example.clonekinopoisk.databinding.ItemFilmCardViewBinding

//Used where id = filmID (search fragment)
class ListFilmsWithFilmIdAdapter(
    private  val onFilmClick:(id:String) -> Unit
)
    : ListAdapter<FilmWithFilmId, ListFilmsWithFilmIdAdapter.ListFilmsWithFilmIViewHolder>(object : DiffUtil.ItemCallback<FilmWithFilmId>(){

    override fun areItemsTheSame(oldItem: FilmWithFilmId, newItem: FilmWithFilmId): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FilmWithFilmId, newItem: FilmWithFilmId): Boolean {
        return oldItem == newItem
    }
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFilmsWithFilmIViewHolder {
        return ListFilmsWithFilmIViewHolder(
            ItemFilmCardViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListFilmsWithFilmIViewHolder, position: Int) {
        holder.bind(getItem(position),onFilmClick)
    }


    class ListFilmsWithFilmIViewHolder(private val binding: ItemFilmCardViewBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind (film: FilmWithFilmId, onFilmClick:(id:String) -> Unit){
            if (film.rating.isNullOrEmpty()){
                binding.ratingFilmCard.text = " "
            }else{
                binding.ratingFilmCard.text = film.rating
                binding.ratingFilmCard.visibility = View.VISIBLE
            }

            binding.posterFilmCard.run {
                Glide.with(context)
                    .load(film.posterUrl)
                    .into(this)
            }

            binding.posterFilmCard.setOnClickListener{
                onFilmClick(film.id)
            }
        }
    }

}