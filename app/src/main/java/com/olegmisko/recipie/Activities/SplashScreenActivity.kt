package com.olegmisko.recipie.Activities


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import com.olegmisko.recipie.Config.LOGIN_STATE
import com.olegmisko.recipie.R
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.android.synthetic.main.activity_splash_screen.view.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast

class SplashScreenActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        setContentView(R.layout.activity_splash_screen)

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

    private fun hideStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun checkLoginState() : Boolean {
       return this.getSharedPreferences(com.olegmisko.recipie.Config.PREFS_NAME, 0).getBoolean(LOGIN_STATE, false)
    }

}
