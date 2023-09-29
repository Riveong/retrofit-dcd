package com.riveong.retrofit_dcd.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class SectionsPagerAdapter(activity: AppCompatActivity, private val username: String?) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = FragmentFollowers()
                username.let {
                    (fragment as FragmentFollowers).arguments = Bundle().apply {
                        putString(FragmentFollowers.ARG_USERNAME, it)
                    }
                }
            }
            1 -> {fragment = FragmentFollowing()
            username.let {
                (fragment as FragmentFollowing).arguments = Bundle().apply {
                    putString(FragmentFollowing.ARG_USERNAME, it)
                }
            }

            }
        }
        return fragment as Fragment
    }

}