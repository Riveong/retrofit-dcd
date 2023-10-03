package com.riveong.retrofit_dcd.ui

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.riveong.retrofit_dcd.R
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.riveong.retrofit_dcd.data.normal.bookmarkDataclass
import com.riveong.retrofit_dcd.data.response.GithubResponse
import com.riveong.retrofit_dcd.data.response.ItemsItem
import com.riveong.retrofit_dcd.data.retrofit.ApiConfig
import com.riveong.retrofit_dcd.databinding.ActivityBookmarkListBinding
import com.riveong.retrofit_dcd.databinding.ActivityMainBinding
import com.riveong.retrofit_dcd.ui.model.SavedAddUpdateViewModel
import com.riveong.retrofit_dcd.ui.model.SavedMainViewModel
import com.riveong.retrofit_dcd.ui.model.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class BookmarkList : AppCompatActivity() {
    private lateinit var binding: ActivityBookmarkListBinding

    companion object{
        private const val TAG = "BookmarkList"
        private const val GITHUB_USER = "Gabriel"


    }
    private lateinit var savedMainViewModel: SavedMainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        Toast.makeText(this@BookmarkList,"Getting data from database, hold on",Toast.LENGTH_SHORT).show()
        Thread.sleep(1000)
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvReview.layoutManager = layoutManager

        //making the Itemsitem mock
        val itemTiru: MutableList<bookmarkDataclass> = mutableListOf()

        //get data from db
        savedMainViewModel = obtainViewModel(this@BookmarkList)
        savedMainViewModel.getAllSaved().observe(this) { savedList ->

            if (savedList != null) {


                for (loopers in savedList) {
                    println(loopers.username)
                    if (loopers.username != null) {
                        val gatau = bookmarkDataclass(loopers.username, loopers.avatarUrl)
                        itemTiru.add(gatau)


                    }


                }

            }

            println("IDK MAN")
            println(itemTiru)
            setUserAnuData(itemTiru)
        }











    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this@BookmarkList,"Getting data from database, hold on",Toast.LENGTH_SHORT).show()

        Thread.sleep(1000)

        //making the Itemsitem mock
        val itemTiru: MutableList<bookmarkDataclass> = mutableListOf()

        //get data from db
        savedMainViewModel = obtainViewModel(this@BookmarkList)
        savedMainViewModel.getAllSaved().observe(this) { savedList ->

            if (savedList != null) {

                for (loopers in savedList) {
                    val gatau = bookmarkDataclass(loopers.username, loopers.avatarUrl)
                    if (loopers.username != null) {
                        if (itemTiru.contains(gatau)){
                            println("udah ada pak")
                        }
                        else {itemTiru.add(gatau)}







                    }


                }

            }

            println("IDK MAN")
            println(itemTiru)

            setUserAnuData(itemTiru)
        }


    }


    private fun obtainViewModel(activity: AppCompatActivity): SavedMainViewModel {

        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(SavedMainViewModel::class.java)


    }

    private fun setUserAnuData(githubItems: List<bookmarkDataclass>) {
        val adapter = BookmarkAdapter()
        adapter.submitList(githubItems)
        adapter.notifyDataSetChanged()
        binding.rvReview.adapter = adapter
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}