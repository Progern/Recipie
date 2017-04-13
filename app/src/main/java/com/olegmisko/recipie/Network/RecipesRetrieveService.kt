package com.olegmisko.recipie.Network

import com.olegmisko.recipie.Models.Response
import retrofit2.Call

class RecipesRetrieveService(private val api: RequestService = RequestService()) {

    fun getRecipes(q: String): Call<Response> {
        return api.getRecipes(q)
    }
}
