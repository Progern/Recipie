package com.olegmisko.recipie.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.olegmisko.recipie.Models.Recipe
import com.olegmisko.recipie.R
import com.olegmisko.recipie.Views.RecipeAdapter
import kotlinx.android.synthetic.main.activity_search_recipes.*
import kotlinx.android.synthetic.main.activity_search_recipes.view.*


class SearchRecipesActivity : AppCompatActivity() {

    private lateinit var recipesTestList: ArrayList<Recipe>
    private lateinit var recipesAdapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_recipes)
        recipesTestList = ArrayList<Recipe>()
        recipesTestList.add(Recipe("Sushi", "http://oyakis.com/wp-content/uploads/2015/06/maki-sushi-roll1.png"))
        recipesTestList.add(Recipe("Soup", "http://thewoksoflife.com/wp-content/uploads/2015/10/egg-drop-soup-8.jpg"))
        recipesTestList.add(Recipe("Steak", "https://www.kingsford.com/wp-content/uploads/2014/11/kfd-hoetosteak-Steak_4_0323-1024x621.jpg"))
        recipesAdapter = RecipeAdapter(recipesTestList)
        val mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        activity_search_recipes.recipesRecyclerView.adapter = recipesAdapter
        activity_search_recipes.recipesRecyclerView.layoutManager = mLayoutManager


    }
}
