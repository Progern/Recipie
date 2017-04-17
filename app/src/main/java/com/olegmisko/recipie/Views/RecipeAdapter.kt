package com.olegmisko.recipie.Views

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.olegmisko.recipie.Activities.BrowserActivity
import com.olegmisko.recipie.Models.Recipe
import com.olegmisko.recipie.R
import com.olegmisko.recipie.Services.DatabaseService
import com.olegmisko.recipie.Services.DownloadRecipeService
import com.olegmisko.recipie.Services.SharingService
import com.squareup.picasso.Picasso
import io.realm.Realm
import kotlinx.android.synthetic.main.recipe_item_layout.view.*
import org.jetbrains.anko.image
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast


class RecipeAdapter(val recipesList: ArrayList<Recipe>) :
        RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item_layout, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holderRecipe: RecipeViewHolder, position: Int) {
        holderRecipe.bindRecipe(this, recipesList[position])
        holderRecipe.itemView.like.image = ContextCompat.getDrawable(holderRecipe.itemView.context,
                if(recipesList[position].isFavorite) R.drawable.ic_star_after_like else R.drawable.ic_star_before_like)
    }


    override fun getItemCount() = recipesList.size

    class RecipeViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindRecipe(adapter: RecipeAdapter, recipe: Recipe) {
            with(recipe) {
                Picasso.with(itemView.context).load(recipe.image).into(itemView.recipeImage)
                itemView.title.text = recipe.label
                Realm.init(itemView.context)
                itemView.calories.text = "Calories: " + recipe.calories
                itemView.total_weight.text = "Total weight: " + recipe.totalWeight
                itemView.ingredients.text = DownloadRecipeService.getIngredientsAsString(recipe.ingredients)
                itemView.expandableLayout.collapse()

                /* On Click section */

                itemView.save.onClick {
                    if (DownloadRecipeService.isExternalStorageWritable() || DownloadRecipeService.isExternalStorageWritable()) {
                        DownloadRecipeService.saveRecipeFileToPhone(recipe, itemView.context)
                    }
                }

                itemView.like.onClick {
                    if (recipe.isFavorite) {
                        itemView.like.image = ContextCompat.getDrawable(itemView.context, R.drawable.ic_star_before_like)
                        recipe.isFavorite = false
                        adapter.notifyDataSetChanged()
                        DatabaseService.removeRecipeFromFavorites(recipe)
                        itemView.context.toast("Recipe removed from favorites.")
                    } else {
                        itemView.like.image = ContextCompat.getDrawable(itemView.context, R.drawable.ic_star_after_like)
                        recipe.isFavorite = true
                        adapter.notifyDataSetChanged()
                        DatabaseService.addNewFavoriteRecipe(recipe)
                        itemView.context.toast("Recipe added to favorites.")
                    }

                }

                itemView.share.onClick {
                    SharingService.shareRecipe(itemView.context, recipe)
                }

                itemView.expand.onClick {
                    if (!itemView.expandableLayout.isExpanded) {
                        itemView.expandableLayout.expand()
                        val animation = AnimationUtils.loadAnimation(view.context, R.anim.rotate)
                        animation.fillAfter = true
                        itemView.expand.startAnimation(animation)

                    } else {
                        itemView.expandableLayout.collapse()
                        val animation = AnimationUtils.loadAnimation(view.context, R.anim.rotate_reverse)
                        animation.fillAfter = true
                        itemView.expand.startAnimation(animation)
                    }
                }

                itemView.recipeImage.onClick {
                    var loadRecipeIntent = Intent(itemView.context, BrowserActivity::class.java)
                    loadRecipeIntent.putExtra("URL", recipe.url)
                    loadRecipeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    itemView.context.startActivity(loadRecipeIntent)

                }

                /* End of onClick section */
            }
        }


    }

}
