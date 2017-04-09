package com.olegmisko.recipie.Services

import com.olegmisko.recipie.Config.APP_ID
import com.olegmisko.recipie.Config.APP_KEY
import com.olegmisko.recipie.Config.BASE_URL
import com.olegmisko.recipie.Models.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RequestService {

    private val requestAPI: RequestAPI

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        requestAPI = retrofit.create(RequestAPI::class.java)
    }

    fun getRecipes(q: String): Call<Response> {
        return requestAPI.getRecipes(q, APP_ID, APP_KEY)
    }
}
