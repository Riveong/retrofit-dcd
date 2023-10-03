package com.riveong.retrofit_dcd.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.riveong.retrofit_dcd.R
import com.riveong.retrofit_dcd.data.response.DetailResponse
import com.riveong.retrofit_dcd.data.retrofit.ApiConfig
import com.riveong.retrofit_dcd.database.Saved
import com.riveong.retrofit_dcd.databinding.ActivityDetailedBinding
import com.riveong.retrofit_dcd.ui.model.SavedAddUpdateViewModel
import com.riveong.retrofit_dcd.ui.model.ViewModelFactory
import com.riveong.retrofit_dcd.database.SavedDao
import com.riveong.retrofit_dcd.databinding.ActivityBookmarkListBinding
import com.riveong.retrofit_dcd.databinding.ActivityDetailBookmarkBinding
import com.riveong.retrofit_dcd.ui.model.SavedMainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailBookmarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBookmarkBinding

    companion object {
        const val EXTRA_TES = "extra_test"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_AVATAR = "extra_avatar"
        const val EXTRA_SAVED = "extra_saved"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_lay1,
            R.string.tab_lay2
        )
    }

    private var fragmentManager: FragmentManager? = null
    private var saved: Saved? = null


    private lateinit var savedAddUpdateViewModel: SavedAddUpdateViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fragmentManager = supportFragmentManager
        var isedit: Boolean
        val extraUsername = intent.getStringExtra(EXTRA_TES)
        val extraName = intent.getStringExtra(EXTRA_NAME)
        val extraAvatar = intent.getStringExtra(EXTRA_AVATAR)
        getDetailedUser(extraUsername)

        var AvatarUrl = getDetailedUser(extraUsername)
        Log.w("BISA GA NIH",AvatarUrl)

        binding.testView.text = extraName
        binding.name.text = extraName

        val tabL = binding.tabLayout

        val sectionsPagerAdapter = SectionsPagerAdapter(this, extraName)
        val viewPager: ViewPager2 = binding.pagination
        viewPager.adapter = sectionsPagerAdapter

        //anunya
        savedAddUpdateViewModel= obtainViewModel(this@DetailBookmarkActivity)
        val savedMainViewModel = obtainViewModel2(this@DetailBookmarkActivity)






        binding.fabAdd.setOnClickListener {
            var username:String = extraUsername!!
            var avatarURL:String? = extraAvatar

            var compare = false

            savedMainViewModel.getAllSaved().observe(this) { savedList ->

                if (savedList != null) {
                    for (loopers in savedList) {
                        println(loopers.username)
                        if (username == loopers.username) {
                            println("kembar nih")
                            compare = true
                            print(compare)

                        }


                    }

                }





                if(compare){

                    Toast.makeText(this@DetailBookmarkActivity,"Already Deleted (bye bye) ", Toast.LENGTH_SHORT).show()
                    saved = Saved(username,avatarURL)
                    savedAddUpdateViewModel.delete(saved as Saved)
                    saved = null






                } else if (!compare){
                    Toast.makeText(this@DetailBookmarkActivity,"Already Deleted", Toast.LENGTH_SHORT).show()

                }

            }






        }









        TabLayoutMediator(tabL, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }


    private fun obtainViewModel(activity: AppCompatActivity): SavedAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(SavedAddUpdateViewModel::class.java)

    }
    private fun obtainViewModel2(activity: AppCompatActivity): SavedMainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(SavedMainViewModel::class.java)

    }



    private fun getDetailedUser(username:String?):String {
        var avatar = "KOSONG BANG"
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
                        avatar = responseBody.avatarUrl
                        // Get the list of followers from the response body
                        val userDetailInfo = responseBody
                        SetDetailedUser(userDetailInfo)


                    }
                } else {
                    // Handle error
                    avatar = "error"
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {

            }
        })
        return avatar
    }
    private fun SetDetailedUser(Detail: DetailResponse) {
        Glide.with(this@DetailBookmarkActivity)
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