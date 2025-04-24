package com.example.doc_schedule.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.doc_schedule.Activity.DetailActivity
import com.example.doc_schedule.Domain.DoctorsModel
import com.example.doc_schedule.Manager.WishlistManager
import com.example.doc_schedule.R
import com.example.doc_schedule.databinding.ViewholderWishlistBinding

class WishlistAdapter(val items: MutableList<DoctorsModel>) :
    RecyclerView.Adapter<WishlistAdapter.Viewholder>() {

    private var context: Context? = null

    class Viewholder(val binding: ViewholderWishlistBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        context = parent.context
        val binding = ViewholderWishlistBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val doctor = items[position]

        holder.binding.nameTxt.text = doctor.Name
        holder.binding.specialTxt.text = doctor.Special
        holder.binding.scoreTxt.text = doctor.Rating.toString()
        holder.binding.ratingBar.rating = doctor.Rating.toFloat()
        holder.binding.degreeTxt.text = "Professional Doctor"

        Glide.with(holder.itemView.context)
            .load(doctor.Picture)
            .apply { RequestOptions().transform(CenterCrop()) }
            .into(holder.binding.img)

        holder.binding.makeBtn.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("object", doctor)
            context?.startActivity(intent)
        }

        holder.binding.favBtn.setOnClickListener {
            val doctor = items[position]

            if (WishlistManager.contains(doctor)) {
                WishlistManager.remove(doctor)
                Toast.makeText(context, "Removed from wishlist", Toast.LENGTH_SHORT).show()
                holder.binding.favBtn.setImageResource(R.drawable.favorite_white) // change to unfilled icon
            } else {
                WishlistManager.add(doctor)
                Toast.makeText(context, "Added to wishlist", Toast.LENGTH_SHORT).show()
                holder.binding.favBtn.setImageResource(R.drawable.fav_bold) // filled icon
            }

            // Optional: notify change if updating UI
            notifyItemChanged(position)
        }

    }

    override fun getItemCount(): Int = items.size



}
