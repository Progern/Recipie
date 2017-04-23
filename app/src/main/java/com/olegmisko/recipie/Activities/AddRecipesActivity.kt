package com.olegmisko.recipie.Activities

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.widget.EditText
import com.olegmisko.recipie.Config.ADDED_RECIPES_ID
import com.olegmisko.recipie.Models.Ingredient
import com.olegmisko.recipie.Models.Recipe
import com.olegmisko.recipie.R
import com.olegmisko.recipie.Services.DatabaseService
import com.olegmisko.recipie.Views.RecipeAdapter
import com.yarolegovich.lovelydialog.LovelyCustomDialog
import io.realm.Realm
import io.realm.RealmList
import kotlinx.android.synthetic.main.activity_add_recipes.*
import kotlinx.android.synthetic.main.activity_add_recipes.view.*
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

        activity_add_recipes.fab_add.onClick {
            showAddRecipeDialog()
        }

        custom_action_bar.title_text.text = "Add recipe"

        progressDialog = indeterminateProgressDialog("Loading recipes…")
        recipesAdapter = RecipeAdapter(getRecipesFromDatabase())
        val mLayoutManager = LinearLayoutManager(applicationContext)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        activity_add_recipes.addRecipesRecyclerView.adapter = recipesAdapter
        activity_add_recipes.addRecipesRecyclerView.layoutManager = mLayoutManager
        activity_add_recipes.addRecipesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 5) activity_add_recipes.fab_add.hide()
                else activity_add_recipes.fab_add.show()
            }
        })
        progressDialog.dismiss()
    }

    private fun showAddRecipeDialog() {
        val dialogView = layoutInflater.inflate(R.layout.add_recipes_dialog_view, null)

        LovelyCustomDialog(this)
                .setView(dialogView)
                .setTopColorRes(R.color.colorPrimary)
                .setIcon(R.drawable.ic_add_recipe_cookbook)
                .setListener(R.id.save_recipe_and_add_to_db, true, {
                    val recipeName = dialogView.findViewById(R.id.recipe_name) as EditText
                    val imageURL = dialogView.findViewById(R.id.imageURL) as EditText
                    val recipeURL = dialogView.findViewById(R.id.recipeURL) as EditText
                    val ingredients = dialogView.findViewById(R.id.ingredientsField) as EditText
                    val calories = dialogView.findViewById(R.id.caloriesValue) as EditText
                    val totalWeight = dialogView.findViewById(R.id.totalWeight) as EditText

                    addRecipeToDatabaseAndUpdateView(Recipe(
                            recipeName.text.toString(),
                            imageURL.text.toString(),
                            recipeURL.toString(),
                            RealmList(Ingredient(ingredients.text.toString(), 0F)), // TODO: Create ingredients from field
                            calories.text.toString().toFloat(),
                            totalWeight.text.toString().toFloat(),
                            ADDED_RECIPES_ID,
                            false))
                })
                .setListener(R.id.dismiss_dialog, true, {})
                .show()
    }

    private fun getRecipesFromDatabase(): ArrayList<Recipe> {
        val list = ArrayList<Recipe>()
        DatabaseService.getAllAddedRecipes().toCollection(list)
        return list
    }

    private fun addRecipeToDatabaseAndUpdateView(recipe: Recipe) {
        DatabaseService.addNewCustomRecipe(recipe)
        recipesAdapter.notifyItemInserted(DatabaseService.getAllAddedRecipes().size - 1)
    }
}
