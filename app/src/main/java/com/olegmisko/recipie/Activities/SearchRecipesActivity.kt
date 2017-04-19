package com.olegmisko.recipie.Activities

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import com.olegmisko.recipie.Models.Hit
import com.olegmisko.recipie.Models.Recipe
import com.olegmisko.recipie.Models.Response
import com.olegmisko.recipie.Network.RecipesRetrieveService
import com.olegmisko.recipie.R
import com.olegmisko.recipie.Views.RecipeAdapter
import com.yarolegovich.lovelydialog.LovelyInfoDialog
import com.yarolegovich.lovelydialog.LovelyTextInputDialog
import kotlinx.android.synthetic.main.activity_search_recipes.*
import kotlinx.android.synthetic.main.activity_search_recipes.view.*
import kotlinx.android.synthetic.main.custom_action_bar.*
import kotlinx.android.synthetic.main.custom_action_bar.view.*
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback


class SearchRecipesActivity : AppCompatActivity() {

    private lateinit var recipesAdapter: RecipeAdapter
    private val recipesRetrieveService = RecipesRetrieveService()
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_recipes)

        val customActionBar = supportActionBar
        customActionBar?.setDisplayShowHomeEnabled(false)
        customActionBar?.setDisplayShowTitleEnabled(false)

        val layoutInflater = LayoutInflater.from(this)
        val customView = layoutInflater.inflate(R.layout.custom_action_bar, null)
        customActionBar?.customView = customView
        customActionBar?.setDisplayShowCustomEnabled(true)

        showInfoDialog()

        custom_action_bar.go_back_button.onClick {
            onBackPressed()
        }

        custom_action_bar.title_text.text = "Search recipes"


        progressDialog = indeterminateProgressDialog("Fetching data…")
        progressDialog.dismiss()


        activity_search_recipes.fab_search.onClick {
            showInputSearchQueryDialog()
        }

        activity_search_recipes.recipesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 5) activity_search_recipes.fab_search.hide()
                else activity_search_recipes.fab_search.show()
            }
        })

    }

    private fun performSearchRequest(query: String) {
        val callResponse = recipesRetrieveService.getRecipes(query)
        callResponse.enqueue(object : Callback<Response> {
            override fun onFailure(call: Call<Response>?, t: Throwable?) {
                toast("Failure")
                progressDialog.dismiss()
            }

            override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                if (response != null) {
                    val recipesList = ArrayList<Recipe>()
                    response.body().hits.mapTo(recipesList, Hit::recipe)
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
                .setTopColorRes(R.color.colorPrimaryLight)
                .setTitle("Delicious food is coming!")
                .setMessage("Input recipe name and start your tasteful journey")
                .setIcon(R.drawable.ic_recipes_book)
                .setConfirmButton("Search", { text ->
                    if (!text.isEmpty())
                        progressDialog.show()
                    performSearchRequest(text)
                })
                .show()
    }

    private fun showInfoDialog() {
        LovelyInfoDialog(this)
                .setTopColorRes(R.color.colorPrimaryLight)
                .setIcon(R.drawable.ic_info)
                .setTitle("Extended recipe information")
                .setMessage("To load recipe in built-in web-browser click on recipe image")
                .setNotShowAgainOptionChecked(false)
                .setNotShowAgainOptionEnabled(0)
                .setConfirmButtonText("OK")
                .show()

    }
}
