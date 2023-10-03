package com.riveong.retrofit_dcd.ui.model

import androidx.recyclerview.widget.DiffUtil
import com.riveong.retrofit_dcd.database.Saved

class SavedDiffCallback(private val oldSavedList: List<Saved>, private val newSavedList: List<Saved>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldSavedList.size
    override fun getNewListSize(): Int = newSavedList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldSavedList[oldItemPosition].username == newSavedList[oldItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldSaved = oldSavedList[oldItemPosition]
        val newSaved = newSavedList[newItemPosition]
        return oldSaved.username == newSaved.username && oldSaved.avatarUrl == newSaved.avatarUrl
    }

}