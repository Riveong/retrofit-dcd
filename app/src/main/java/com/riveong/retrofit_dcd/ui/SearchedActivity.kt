package com.riveong.retrofit_dcd.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.riveong.retrofit_dcd.data.response.GithubResponse
import com.riveong.retrofit_dcd.data.response.ItemsItem
import com.riveong.retrofit_dcd.data.retrofit.ApiConfig
import com.riveong.retrofit_dcd.databinding.ActivitySearchedBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchedActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchedBinding

    companion object{
        private const val TAG = "SearchedActivity"
        const val EXTRA_GITHUB_USER = "extra_github_user"


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvReview.layoutManager = layoutManager


        val usernameIntented = intent.getStringExtra(EXTRA_GITHUB_USER)

        findGithub(usernameIntented.toString())
        binding.SAtvTitle.text = usernameIntented
    }


    private fun findGithub(extraidk: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getGithub(extraidk)
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
                        println("===========================================")
                        println(responseBody.items)
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
        binding.SAtvTitle.text = "Found: ${github.totalCount}"
        binding.SAtvDescription.text = "Incomplete results status: ${github.incompleteResults}"


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