package com.riveong.retrofit_dcd.ui.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.riveong.retrofit_dcd.database.Saved
import com.riveong.retrofit_dcd.repository.SavedRepository

class SavedMainViewModel(application: Application) : ViewModel() {

    private val mSavedRepository: SavedRepository = SavedRepository(application)

    fun getAllSaved(): LiveData<List<Saved>> = mSavedRepository.getAllSaved()


}