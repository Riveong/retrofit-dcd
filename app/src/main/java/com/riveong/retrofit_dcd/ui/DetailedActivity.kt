package com.riveong.retrofit_dcd.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.riveong.retrofit_dcd.R
import com.riveong.retrofit_dcd.data.response.DetailResponse
import com.riveong.retrofit_dcd.data.retrofit.ApiConfig
import com.riveong.retrofit_dcd.databinding.ActivityDetailedBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailedBinding

    companion object {
        const val EXTRA_TES = "extra_test"
        const val EXTRA_NAME = "extra_name"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_lay1,
            R.string.tab_lay2
        )
    }

    private var fragmentManager: FragmentManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fragmentManager = supportFragmentManager

        val extraUsername = intent.getStringExtra(EXTRA_TES)
        val extraName = intent.getStringExtra(EXTRA_NAME)
        getDetailedUser(extraUsername)

        binding.testView.text = extraUsername
        binding.name.text = extraName

        val tabL = binding.tabLayout

        val sectionsPagerAdapter = SectionsPagerAdapter(this, extraName)
        val viewPager: ViewPager2 = binding.pagination
        viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(tabL, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    private fun getDetailedUser(username:String?) {
        showLoading(true)
        val client = ApiConfig.getApiService().getDetail(username)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                if (response.isSuccessful) {
                    showLoading(false)
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Get the list of followers from the response body
                        val userDetailInfo = responseBody
                        SetDetailedUser(userDetailInfo)

                    }
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {

            }
        })
    }
    private fun SetDetailedUser(Detail: DetailResponse) {
        Glide.with(this@DetailedActivity)
            .load(Detail.avatarUrl)
            .circleCrop()
            .into(binding.imageView)






        binding.name.text = Detail.name
        binding.theFlex.text = "Followers: ${Detail.followers}\nFollowing: ${Detail.following}"

    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressDetail.visibility = View.VISIBLE
        } else {
            binding.progressDetail.visibility = View.GONE
        }
    }

    }