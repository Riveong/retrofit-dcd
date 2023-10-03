package com.riveong.retrofit_dcd.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Saved (
    @PrimaryKey(autoGenerate = false)
    var username: String = "Thariq",
    var avatarUrl: String? = "https://i.pinimg.com/736x/43/26/bc/4326bc5de92748dd463132f1c56e9add.jpg"

):Parcelable