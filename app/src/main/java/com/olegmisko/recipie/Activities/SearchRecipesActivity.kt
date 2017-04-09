package com.olegmisko.recipie.Activities

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.olegmisko.recipie.Models.Recipe
import com.olegmisko.recipie.Models.Response
import com.olegmisko.recipie.R
import com.olegmisko.recipie.Services.RecipesRetrieveService
import com.olegmisko.recipie.Views.RecipeAdapter
import com.yarolegovich.lovelydialog.LovelyTextInputDialog
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


        activity_search_recipes.fab_search.onClick {
            showInputSearchQueryDialog()
        }
    }

    private fun performSearchRequest(query: String) {
        val callResponse = recipesRetrieveService.getRecipes(query)
        callResponse.enqueue(object : Callback<Response> {
            override fun onFailure(call: Call<Response>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
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

    private fun showInputSearchQueryDialog() {
        LovelyTextInputDialog(this)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle("Delicious food is coming!")
                .setMessage("Input desirable recipe name and start your tasty journey")
                .setIcon(R.drawable.ic_recipes_book)
                .setConfirmButton("Search", { text ->
                    if (!text.isEmpty()) performSearchRequest(text) })
                .show()
    }
}
