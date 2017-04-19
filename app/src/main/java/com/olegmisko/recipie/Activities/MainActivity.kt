package com.olegmisko.recipie.Activities

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.olegmisko.recipie.Config.ActivityHelper
import com.olegmisko.recipie.R
import com.olegmisko.recipie.Services.LoginStateService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.onClick

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityHelper.hideStatusBar(this)
        setContentView(R.layout.activity_main)
        setCustomFonts()

        /* Click listeners section */

        activity_main.searchRecipes.onClick {
            startActivity(intentFor<SearchRecipesActivity>())
        }

        activity_main.add_recipes_text.onClick {
            //TODO: Start add recipes Activity
        }

        activity_main.favoriteRecipes.onClick {
            //TODO: Start favorite recipes Activity
        }

        activity_main.settings.onClick {
            //TODO: Start settings Activity
        }

        activity_main.information.onClick {
            //TODO: Start information Activity
        }

        activity_main.logout.onClick {
            LoginStateService.changeUserStateToLoggedOut(getSharedPreferences(com.olegmisko.recipie.Config.PREFS_NAME, 0))
            startActivity(intentFor<SplashScreenActivity>())
        }

        /* End of click listeners section */
    }


    private fun setCustomFonts() {
        val typeface = Typeface.createFromAsset(assets, "fonts/blessed.ttf")
        activity_main.search_recipes_text.typeface = typeface
        activity_main.add_recipes_text.typeface = typeface
        activity_main.favorite_recipes_text.typeface = typeface
        activity_main.settings_text.typeface = typeface
        activity_main.information_text.typeface = typeface
        activity_main.logout_text.typeface = typeface
    }

    override fun onBackPressed() {
        // Do nothing
    }

}
