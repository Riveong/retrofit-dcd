package com.riveong.retrofit_dcd.ui

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riveong.retrofit_dcd.data.response.ItemsItem
import com.riveong.retrofit_dcd.databinding.ItemReviewBinding

class FollowersAdapter : ListAdapter<ItemsItem, FollowersAdapter.MyViewHolder>(DIFF_CALLBACK) {


    class MyViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem){
            binding.tvItem.text = "${user.login}"
            Glide.with(itemView)
                .load(user.avatarUrl)
                .circleCrop()
                .into(binding.imgItemPhoto)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        Log.w("FollowerAdapter","random")



        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailedActivity::class.java)
            intent.putExtra(DetailedActivity.EXTRA_TES,user.login)
            intent.putExtra(DetailedActivity.EXTRA_NAME,user.login)
            holder.itemView.context.startActivity(intent)





        }

    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}