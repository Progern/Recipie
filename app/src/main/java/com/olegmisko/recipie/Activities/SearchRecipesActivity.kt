package com.olegmisko.recipie.Activities

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.olegmisko.recipie.Models.Recipe
import com.olegmisko.recipie.Models.Response
import com.olegmisko.recipie.R
import com.olegmisko.recipie.Services.RecipesRetrieveService
import com.olegmisko.recipie.Views.RecipeAdapter
import kotlinx.android.synthetic.main.activity_search_recipes.*
import kotlinx.android.synthetic.main.activity_search_recipes.view.*
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback


class SearchRecipesActivity : AppCompatActivity() {

    //private lateinit var recipesTestList: ArrayList<Recipe>
    private lateinit var recipesAdapter: RecipeAdapter
    private val recipesRetrieveService = RecipesRetrieveService()
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_recipes)
        progressDialog = indeterminateProgressDialog("Fetching data…")
        progressDialog.dismiss()
        /*

        recipesTestList = ArrayList<Recipe>()
        recipesTestList.add(Recipe("Sushi", "http://oyakis.com/wp-content/uploads/2015/06/maki-sushi-roll1.png"))
        recipesTestList.add(Recipe("Soup", "http://thewoksoflife.com/wp-content/uploads/2015/10/egg-drop-soup-8.jpg"))
        recipesTestList.add(Recipe("Steak", "https://www.kingsford.com/wp-content/uploads/2014/11/kfd-hoetosteak-Steak_4_0323-1024x621.jpg"))

        */

        /*
        recipesAdapter = RecipeAdapter()
        val mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        activity_search_recipes.recipesRecyclerView.adapter = recipesAdapter
        activity_search_recipes.recipesRecyclerView.layoutManager = mLayoutManager */

        activity_search_recipes.search_recipes_button.onClick {
            if (!activity_search_recipes.recipe_search_field.text.toString().trim().isEmpty()) {
                performSearchRequest(activity_search_recipes.recipe_search_field.text.toString().trim())
                progressDialog.show()
            } else {
                YoYo.with(Techniques.Shake)
                        .duration(500)
                        .playOn(activity_search_recipes.recipe_search_field)
                toast("Please fill in search query.")
            }
        }


    }

    private fun performSearchRequest(query: String) {
        val callResponse = recipesRetrieveService.getRecipes(query)
        callResponse.enqueue(object : Callback<Response> {
            override fun onFailure(call: Call<Response>?, t: Throwable?) {
                toast("Error")

            }

            override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                toast("Success")
                if (response != null) {
                    val recipesList = ArrayList<Recipe>()
                    response.body().hits.mapTo(recipesList) { it.recipe }
                    recipesAdapter = RecipeAdapter(recipesList)
                    val mLayoutManager = LinearLayoutManager(applicationContext)
                    mLayoutManager.orientation = LinearLayoutManager.VERTICAL
                    activity_search_recipes.recipesRecyclerView.adapter = recipesAdapter
                    activity_search_recipes.recipesRecyclerView.layoutManager = mLayoutManager
                    progressDialog.dismiss()
                }

            }

        })
    }
}
