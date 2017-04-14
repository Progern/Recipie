package com.olegmisko.recipie.Views

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import java.util.Collections.rotate
import android.view.animation.AnimationUtils
import android.view.animation.Animation
import android.widget.ImageView


class RecipeAdapter(val recipesList: ArrayList<Recipe>) :
        RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item_layout, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holderRecipe: RecipeViewHolder, position: Int) {
        holderRecipe.bindRecipe(recipesList[position])
    }

    override fun getItemCount() = recipesList.size

    class RecipeViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindRecipe(recipe: Recipe) {
            with(recipe) {
                Picasso.with(itemView.context).load(recipe.image).into(itemView.recipeImage)
                itemView.title.text = recipe.label
                Realm.init(itemView.context)
                itemView.calories.text = "Calories: " + recipe.calories
                itemView.total_weight.text = "Total weight: " + recipe.totalWeight
                itemView.ingredients.text = DownloadRecipeService.getIngredientsAsString(recipe.ingredients)


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
                        DatabaseService.removeRecipeFromFavorites(recipe)
                        itemView.context.toast("Recipe removed from favorites.")
                    } else {
                        itemView.like.image = ContextCompat.getDrawable(itemView.context, R.drawable.ic_star_after_like)
                        recipe.isFavorite = true
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
                        rotateImage(itemView.expand)

                    } else {
                        itemView.expandableLayout.collapse()
                        rotateImage(itemView.expand)
                    }
                }
            }
        }

        fun rotateImage(view : ImageView) {
            val animation = AnimationUtils.loadAnimation(view.context, R.anim.rotate)
            animation.fillAfter = true
            animation.repeatCount = 0
            view.expand.animation = animation
            view.expand.startAnimation(animation)
        }

    }


}
