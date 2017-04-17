package com.olegmisko.recipie.Activities


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import com.olegmisko.recipie.Config.ActivityHelper
import com.olegmisko.recipie.Config.LOGIN_STATE
import com.olegmisko.recipie.R
import com.olegmisko.recipie.Services.DownloadRecipeService
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.android.synthetic.main.activity_splash_screen.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast

class SplashScreenActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityHelper.hideStatusBar(this)
        setContentView(R.layout.activity_splash_screen)
        DownloadRecipeService.checkExternalStoragePermissions(this)

        activity_splash_screen.sign_in_button.onClick {
           if (checkLoginState()) {
               toast("Already logged in")
               startActivity(intentFor<MainActivity>())
           } else {
               startActivity(intentFor<LoginActivity>())
           }
        }

        activity_splash_screen.get_started_button.onClick {
            startActivity(intentFor<RegistrationActivity>())
        }

    }

    private fun checkLoginState() : Boolean {
       return this.getSharedPreferences(com.olegmisko.recipie.Config.PREFS_NAME, 0).getBoolean(LOGIN_STATE, false)
    }

}
