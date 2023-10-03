package com.riveong.retrofit_dcd.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.riveong.retrofit_dcd.database.Saved
import com.riveong.retrofit_dcd.database.SavedDao
import com.riveong.retrofit_dcd.database.SavedRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SavedRepository(application: Application) {
    private val mSavedDao: SavedDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = SavedRoomDatabase.getDatabase(application)
        mSavedDao = db.savedDao()
    }


    fun getAllSaved(): LiveData<List<Saved>> = mSavedDao.getAllSaved()


    fun insert(saved: Saved){
        executorService.execute{ mSavedDao.insert(saved) }
    }

    fun delete(saved: Saved){
        executorService.execute{ mSavedDao.delete(saved) }
    }

    fun update(saved: Saved){
        executorService.execute{ mSavedDao.update(saved) }
    }

    fun check(saved: Saved){
        executorService.execute{ mSavedDao.getCheckifAlready(saved.username) }
    }


}