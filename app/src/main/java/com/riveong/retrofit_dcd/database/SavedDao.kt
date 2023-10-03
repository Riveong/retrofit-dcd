package com.riveong.retrofit_dcd.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface SavedDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(saved: Saved)
    @Update
    fun update(saved: Saved)
    @Delete
    fun delete(saved: Saved)
    @Query("SELECT * from saved")
    fun getAllSaved(): LiveData<List<Saved>>


    @Query("SELECT * FROM saved WHERE username LIKE :username")
    fun getCheckifAlready(username: String): LiveData<List<Saved>>

    @Query("DELETE FROM saved WHERE username = :username")
    fun getDeleteSaved(username: String?)



}