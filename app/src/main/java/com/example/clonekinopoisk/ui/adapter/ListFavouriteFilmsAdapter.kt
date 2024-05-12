package com.example.clonekinopoisk.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clonekinopoisk.data.model.FilmFullInfo
import com.example.clonekinopoisk.databinding.ItemFilmCardViewBinding

class ListFavouriteFilmsAdapter (
    private  val onFilmClick:(id:String) -> Unit
)
    : ListAdapter<FilmFullInfo, ListFavouriteFilmsAdapter.ListFilmFavouriteViewHolder>(object : DiffUtil.ItemCallback<FilmFullInfo>(){

    override fun areItemsTheSame(oldItem: FilmFullInfo, newItem: FilmFullInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FilmFullInfo, newItem: FilmFullInfo): Boolean {
        return oldItem == newItem
    }
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFilmFavouriteViewHolder {
        return ListFilmFavouriteViewHolder(
            ItemFilmCardViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListFilmFavouriteViewHolder, position: Int) {
        holder.bind(getItem(position),onFilmClick)
    }


    class ListFilmFavouriteViewHolder(private val binding: ItemFilmCardViewBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind (film: FilmFullInfo, onFilmClick:(id:String) -> Unit){
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