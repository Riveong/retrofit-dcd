package com.riveong.retrofit_dcd.ui

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riveong.retrofit_dcd.data.normal.bookmarkDataclass
import com.riveong.retrofit_dcd.data.response.ItemsItem
import com.riveong.retrofit_dcd.databinding.ItemReviewBinding

class BookmarkAdapter : ListAdapter<bookmarkDataclass, BookmarkAdapter.MyViewHolder>(DIFF_CALLBACK) {


    class MyViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: bookmarkDataclass){
            binding.tvItem.text = "${user.login}"
            Glide.with(itemView)
                .load(user.avatarurl)
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
        Log.w("Bookmark Adapter","random")




        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailBookmarkActivity::class.java)
            intent.putExtra(DetailBookmarkActivity.EXTRA_TES,user.login)
            intent.putExtra(DetailBookmarkActivity.EXTRA_NAME,user.login)
            intent.putExtra(DetailBookmarkActivity.EXTRA_AVATAR, user.avatarurl)
            holder.itemView.context.startActivity(intent)






        }

    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<bookmarkDataclass>() {
            override fun areItemsTheSame(oldItem: bookmarkDataclass, newItem: bookmarkDataclass): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: bookmarkDataclass, newItem: bookmarkDataclass): Boolean {
                return oldItem == newItem
            }
        }
    }
}