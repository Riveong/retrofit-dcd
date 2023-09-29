package com.riveong.retrofit_dcd.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.riveong.retrofit_dcd.data.response.GithubResponse
import com.riveong.retrofit_dcd.data.response.ItemsItem
import com.riveong.retrofit_dcd.data.retrofit.ApiConfig
import com.riveong.retrofit_dcd.databinding.ActivityMainBinding
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