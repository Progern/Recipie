package com.olegmisko.recipie.Network

import com.olegmisko.recipie.Models.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RequestAPI {

    @GET("search?")
    fun getRecipes(@Query("q") q: String,

                   @Query("app_id") app_id: String,

                   @Query("app_key") app_key: String): Call<Response>
}
