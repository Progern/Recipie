package com.olegmisko.recipie.Activities

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import com.olegmisko.recipie.R
import com.olegmisko.recipie.Services.changeUserStateToLoggedOut
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.onClick

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
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
            changeUserStateToLoggedOut(getSharedPreferences(com.olegmisko.recipie.Config.PREFS_NAME, 0))
            startActivity(intentFor<SplashScreenActivity>())
        }

        /* End of click listeners section */
    }


    /*
        Hides the app bar and the status bar
     */
    private fun hideStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
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

}
