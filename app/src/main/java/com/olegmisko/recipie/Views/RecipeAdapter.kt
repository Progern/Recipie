package com.olegmisko.recipie.Views

import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.olegmisko.recipie.Models.Recipe
import com.olegmisko.recipie.R
import com.olegmisko.recipie.Services.DatabaseService
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
        holderRecipe.bindRecipe(recipesList[position])
    }

    override fun getItemCount() = recipesList.size

    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var isLiked: Boolean = false

        fun bindRecipe(recipe: Recipe) {
            with(recipe) {
                Picasso.with(itemView.context).load(recipe.image).into(itemView.recipeImage)
                itemView.title.text = recipe.label
                itemView.save.onClick {
                    // Invoke download manager

                }

                itemView.like.onClick {
                    Realm.init(itemView.context)
                    if (isLiked) {
                        makeRecipeUnLiked(itemView.like)
                        isLiked = false
                        DatabaseService.removeRecipeFromFavorites(recipe)
                    } else {
                        makeRecipeLiked(itemView.like)
                        isLiked = true
                        DatabaseService.addNewFavoriteRecipe(recipe)
                    }

                }

                itemView.share.onClick {
                    // Invoke share manager
                }

                itemView.expand.onClick {
                    // Invoke share manager
                }
            }
        }

        private fun makeRecipeUnLiked(view : ImageView) {
            val transitionDrawable = TransitionDrawable(arrayOf<Drawable>(ContextCompat.getDrawable(view.context, R.drawable.ic_star_before_like), ContextCompat.getDrawable(itemView.context, R.drawable.ic_star_after_like)))
            view.image = transitionDrawable
            transitionDrawable.startTransition(500)
        }

        private fun makeRecipeLiked(view : ImageView) {
            val transitionDrawable = TransitionDrawable(arrayOf<Drawable>(ContextCompat.getDrawable(view.context, R.drawable.ic_star_after_like), ContextCompat.getDrawable(itemView.context, R.drawable.ic_star_before_like)))
            view.image = transitionDrawable
            transitionDrawable.startTransition(500)
        }
    }



}
