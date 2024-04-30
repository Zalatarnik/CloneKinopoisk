package com.example.clonekinopoisk.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clonekinopoisk.data.PersonInStaff
import com.example.clonekinopoisk.databinding.ItemPersonCardBinding

class PersonInStuffAdapter()
    : ListAdapter<PersonInStaff, PersonInStuffAdapter.PersonInStuffViewHolder>
    (object : DiffUtil.ItemCallback<PersonInStaff>(){

    override fun areItemsTheSame(oldItem: PersonInStaff, newItem: PersonInStaff): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: PersonInStaff, newItem: PersonInStaff): Boolean {
        return false
    }
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonInStuffViewHolder {
        return PersonInStuffViewHolder(
            ItemPersonCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PersonInStuffViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class PersonInStuffViewHolder(private val binding: ItemPersonCardBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind (person: PersonInStaff){
            if (person.nameEn.isNullOrEmpty()){
                binding.naePersonCard.text = " "
            }else{
                binding.naePersonCard.text = person.nameEn
            }

            binding.professionPersonCard.text = person.professionText ?: "N/A"

            binding.imagePersonCard.run {
                Glide.with(context)
                    .load(person.posterUrl)
                    .into(this)
            }

        }
    }

}