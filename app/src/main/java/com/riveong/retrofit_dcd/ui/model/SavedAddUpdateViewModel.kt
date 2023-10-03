package com.riveong.retrofit_dcd.ui.model

import android.app.Application
import androidx.lifecycle.ViewModel
import com.riveong.retrofit_dcd.database.Saved
import com.riveong.retrofit_dcd.repository.SavedRepository

class SavedAddUpdateViewModel(application: Application) : ViewModel() {
    private val mSavedRepository: SavedRepository = SavedRepository(application)
    fun insert(saved: Saved){
        mSavedRepository.insert(saved)
    }

    fun delete(saved: Saved){
        mSavedRepository.delete(saved)
    }

    fun update(saved: Saved){
        mSavedRepository.update(saved)
    }

    fun check(saved: Saved){
        mSavedRepository.check(saved)
    }


}



