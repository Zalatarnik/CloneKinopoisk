package com.example.clonekinopoisk.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clonekinopoisk.data.Film
import com.example.clonekinopoisk.databinding.ItemFilmCardViewBinding

class ListFilmsAdapter(
    private  val onFilmClick:(id:String) -> Unit
)
    : ListAdapter<Film, ListFilmsAdapter.ListFilmViewHolder>(object : DiffUtil.ItemCallback<Film>(){

    override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
        return false
    }
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFilmViewHolder {
        return ListFilmViewHolder(
            ItemFilmCardViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListFilmViewHolder, position: Int) {
        holder.bind(getItem(position),onFilmClick)
    }


class ListFilmViewHolder(private val binding: ItemFilmCardViewBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind (film: Film,onFilmClick:(id:String) -> Unit){
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