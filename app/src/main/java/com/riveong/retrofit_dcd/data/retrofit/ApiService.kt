package com.riveong.retrofit_dcd.data.retrofit
import com.riveong.retrofit_dcd.data.response.RestaurantResponse
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @GET("detail/{id}")
    fun getRestaurant(
        @Path("id") id: String
    ): Call<RestaurantResponse>
}