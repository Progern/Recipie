package com.olegmisko.recipie.Views

import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.olegmisko.recipie.Models.Recipe
import com.olegmisko.recipie.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recipe_item_layout.view.*
import org.jetbrains.anko.image
import org.jetbrains.anko.onClick


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
                    if (isLiked) {
                        val transitionDrawable = TransitionDrawable(arrayOf<Drawable>(ContextCompat.getDrawable(itemView.context, R.drawable.ic_star_before_like), ContextCompat.getDrawable(itemView.context, R.drawable.ic_star_after_like)))
                        itemView.like.image = transitionDrawable
                        transitionDrawable.startTransition(500)
                        isLiked = false
                        // Remove from "favorites"
                    } else {
                        val transitionDrawable = TransitionDrawable(arrayOf<Drawable>(ContextCompat.getDrawable(itemView.context, R.drawable.ic_star_after_like), ContextCompat.getDrawable(itemView.context, R.drawable.ic_star_before_like)))
                        itemView.like.image = transitionDrawable
                        transitionDrawable.startTransition(500)
                        isLiked = true
                        // Add to "favorites"
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
    }

}
