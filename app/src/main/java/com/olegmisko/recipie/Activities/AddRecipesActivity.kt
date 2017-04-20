package com.olegmisko.recipie.Activities

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.olegmisko.recipie.Models.Recipe
import com.olegmisko.recipie.R
import com.olegmisko.recipie.Services.DatabaseService
import com.olegmisko.recipie.Views.RecipeAdapter
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_add_recipes.*
import kotlinx.android.synthetic.main.activity_add_recipes.view.*
import kotlinx.android.synthetic.main.activity_search_recipes.*
import kotlinx.android.synthetic.main.activity_search_recipes.view.*
import kotlinx.android.synthetic.main.custom_action_bar.*
import kotlinx.android.synthetic.main.custom_action_bar.view.*
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.onClick

class AddRecipesActivity : AppCompatActivity() {

    private lateinit var recipesAdapter: RecipeAdapter
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipes)

        val customActionBar = supportActionBar
        customActionBar?.setDisplayShowHomeEnabled(false)
        customActionBar?.setDisplayShowTitleEnabled(false)

        val layoutInflater = LayoutInflater.from(this)
        val customView = layoutInflater.inflate(R.layout.custom_action_bar, null)
        customActionBar?.customView = customView
        customActionBar?.setDisplayShowCustomEnabled(true)

        Realm.init(this)

        custom_action_bar.go_back_button.onClick {
            onBackPressed()
        }

        custom_action_bar.title_text.text = "Add recipe"

        progressDialog = indeterminateProgressDialog("Loading recipes…")
        recipesAdapter = RecipeAdapter(getRecipesFromDatabase())
        val mLayoutManager = LinearLayoutManager(applicationContext)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        activity_add_recipes.addRecipesRecyclerView.adapter = recipesAdapter
        activity_add_recipes.addRecipesRecyclerView.layoutManager = mLayoutManager
        progressDialog.dismiss()
    }

    private fun showAddRecipeDialog() {

    }

    private fun getRecipesFromDatabase() : ArrayList<Recipe> {
        val list = ArrayList<Recipe>()
        DatabaseService.getAllAddedRecipes().toCollection(list)
        return list
    }

    private fun addRecipeToDatabaseAndUpdateView() {

    }
}
