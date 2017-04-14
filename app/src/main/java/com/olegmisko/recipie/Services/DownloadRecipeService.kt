package com.olegmisko.recipie.Services

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.support.v4.app.ActivityCompat
import com.olegmisko.recipie.Config.REQUEST_CODE
import com.olegmisko.recipie.Models.Ingredient
import com.olegmisko.recipie.Models.Recipe
import io.realm.RealmList
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileWriter
import java.io.IOException


internal object DownloadRecipeService {


    fun saveRecipeFileToPhone(recipe: Recipe, context: Context) {
        if (isExternalStorageWritable() || isExternalStorageReadable()) {
            try {
                val root = File(Environment.getExternalStorageDirectory(), "Recipes")
                if (!root.exists()) {
                    root.mkdirs()
                }
                val recipeFile = File(root, validateRecipeLabel(recipe.label))
                val writer = FileWriter(recipeFile)
                writer.append(createTextFileFromRecipe(recipe))
                writer.flush()
                writer.close()
                context.toast("Recipe saved.")

            } catch (e: IOException) {
                e.printStackTrace()
                context.toast("Error while saving recipe.")
            }
        }

    }

    private fun createTextFileFromRecipe(recipe: Recipe): String {
        return "Name: " + recipe.label + "\nCalories: " + recipe.calories + "\nIngredients:\n" + getIngredientsAsString(recipe.ingredients) + "\nMore:\n" + recipe.url
    }

    fun checkExternalStoragePermissions(activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    activity.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true
            } else {
                askExternalStoragePermissions(activity)
                return false
            }
        }
        return false
    }

    fun askExternalStoragePermissions(activity: Activity) {
        ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE)
    }

    fun isExternalStorageWritable(): Boolean {
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED.equals(state)) return true
        return false
    }

    fun isExternalStorageReadable(): Boolean {
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) return true
        return false
    }

    fun validateRecipeLabel(name: String): String {
        return name.replace("[^a-zA-Z0-9.-]", "_") + ".txt"
    }

    fun getIngredientsAsString(ingredients: RealmList<Ingredient>?): String {
        val buffer: StringBuffer = StringBuffer()
        if (ingredients != null) {
            for (ingredient in ingredients) {
                buffer.append("* " + ingredient.text + ", weight: " + ingredient.weight + "\n")
            }
        }
        return buffer.toString()
    }

}
