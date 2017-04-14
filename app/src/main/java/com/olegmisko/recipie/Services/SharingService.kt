package com.olegmisko.recipie.Services

import android.content.Context
import android.content.Intent
import com.olegmisko.recipie.Models.Recipe

internal object SharingService {

     fun shareRecipe(context: Context, recipe: Recipe) {
        var sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.putExtra(Intent.EXTRA_TEXT, recipe.label + "\n\n" + "See more.. " + recipe.url)
        sharingIntent.type = "text/plain"
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(sharingIntent)
    }
}