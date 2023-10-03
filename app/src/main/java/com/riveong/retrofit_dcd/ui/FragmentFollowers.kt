package com.riveong.retrofit_dcd.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.riveong.retrofit_dcd.data.response.ItemsItem
import com.riveong.retrofit_dcd.databinding.FragmentFollowersBinding
import com.riveong.retrofit_dcd.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentFollowers : Fragment() {

    private lateinit var binding: FragmentFollowersBinding



    companion object {
        const val ARG_USERNAME = "Riveong"
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)


        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(ARG_USERNAME)

        // Set the adapter to the RecyclerView
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.followersRV.layoutManager = layoutManager
        fetchFollowers(username)



    }


    private fun fetchFollowers(username:String? ) {
        showLoading(true)
        binding.statusText.visibility = View.INVISIBLE
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful) {
                    showLoading(false)
                    val responseBody = response.body()
                    if (!responseBody.isNullOrEmpty()) {

                        binding.statusText.visibility = View.GONE
                        val followers = responseBody
                        Log.e("FOLLOWERS",followers.toString())
                        setFollowersAnuData(followers)
                    } else {
                        showLoading(false)
                        binding.statusText.visibility = View.VISIBLE

                    }
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                showLoading(false)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.indicator.visibility = View.VISIBLE
        } else {
            binding.indicator.visibility = View.GONE
        }
    }
    private fun setFollowersAnuData(followers: List<ItemsItem>) {


        val adapter = FollowersAdapter()
        adapter.submitList(followers)
        binding.followersRV.adapter = adapter
        Log.i("FragmentFollowers","random")
    }
}


