package com.olegmisko.recipie.Services

import com.olegmisko.recipie.Config.ADDED_RECIPES_ID
import com.olegmisko.recipie.Config.FAVORITE_RECIPES_ID
import com.olegmisko.recipie.Models.Recipe
import io.realm.Realm
import io.realm.RealmResults


internal object DatabaseService {

    val instance = DatabaseService
    val realmInstance = Realm.getDefaultInstance()

    fun addNewFavoriteRecipe(recipe: Recipe) {
        realmInstance.beginTransaction()
        recipe.id = FAVORITE_RECIPES_ID
        realmInstance.copyToRealm(recipe)
        realmInstance.commitTransaction()
    }

    fun removeRecipeFromFavorites(recipe: Recipe) {
        realmInstance.beginTransaction()
        realmInstance.where(Recipe::class.java).equalTo("id", 0).equalTo("label", recipe.label).findAll().deleteFirstFromRealm()
        realmInstance.commitTransaction()
    }

    fun addNewCustomRecipe(recipe : Recipe){
        realmInstance.beginTransaction()
        recipe.id = ADDED_RECIPES_ID
        realmInstance.copyToRealm(recipe)
        realmInstance.commitTransaction()
    }

    fun removeRecipeFromAdded(recipe: Recipe) {
        realmInstance.beginTransaction()
        realmInstance.where(Recipe::class.java).equalTo("id", 1).equalTo("label", recipe.label).findAll().deleteFirstFromRealm()
        realmInstance.commitTransaction()
    }

    fun getAllFavoriteRecipes(): RealmResults<Recipe> {
        return realmInstance.where(Recipe::class.java)
                .equalTo("id", 0).findAll()
    }

    fun getAllAddedRecipes(): RealmResults<Recipe> {
        return realmInstance.where(Recipe::class.java)
                .equalTo("id", 1).findAll()
    }

}
