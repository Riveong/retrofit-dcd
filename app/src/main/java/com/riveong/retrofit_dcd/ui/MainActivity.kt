package com.riveong.retrofit_dcd.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.switchmaterial.SwitchMaterial
import com.riveong.retrofit_dcd.data.response.GithubResponse
import com.riveong.retrofit_dcd.data.response.ItemsItem
import com.riveong.retrofit_dcd.data.retrofit.ApiConfig
import com.riveong.retrofit_dcd.databinding.ActivityMainBinding
import com.riveong.retrofit_dcd.ui.model.MainViewModel
import com.riveong.retrofit_dcd.ui.model.SettingPreferences
import com.riveong.retrofit_dcd.ui.model.ViewModelFactoryTheme
import com.riveong.retrofit_dcd.ui.model.dataStore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object{
        private const val TAG = "MainActivity"
        private const val GITHUB_USER = "Gabriel"


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvReview.layoutManager = layoutManager





        val switchTheme = binding.ThemeSwitch

        val pref = SettingPreferences.getInstance(application.dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactoryTheme(pref)).get(
            MainViewModel::class.java
        )
        mainViewModel.getThemeSettings().observe(this){isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }


        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }



        findGithub()
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val userInputs = searchView.query.toString()
                val searchintent = Intent(this@MainActivity, SearchedActivity::class.java)
                searchintent.putExtra(SearchedActivity.EXTRA_GITHUB_USER, userInputs)
                startActivity(searchintent)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })



        binding.btnBookmark.setOnClickListener {
            val intent = Intent(this, BookmarkList::class.java)
            startActivity(intent)
        }



    }


    private fun findGithub() {
        showLoading(true)
        val client = ApiConfig.getApiService().getGithub(GITHUB_USER)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setGithubData(responseBody)
                        setUserAnuData(responseBody.items)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }



    private fun setGithubData(github: GithubResponse){
        binding.tvTitle.text = "Found: ${github.totalCount}"
        binding.tvDescription.text = "Incomplete results status: ${github.incompleteResults}"


    }

    private fun setUserAnuData(githubItems: List<ItemsItem>) {
        val adapter = GithubAdapter()
        adapter.submitList(githubItems)
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