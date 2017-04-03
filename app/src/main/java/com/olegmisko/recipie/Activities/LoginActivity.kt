package com.olegmisko.recipie.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.olegmisko.recipie.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_registration.*
import org.jetbrains.anko.onClick


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        setContentView(R.layout.activity_login)

        activity_login.submit_button.onClick {
            if (checkUsernameField() && checkPasswordField()) {
                // Send Firebase request with specified credentials
            } else {
                
            }
        }

        activity_login.facebook_submit.onClick {
            // Handle Facebook login operation
        }

        activity_login.google_submit.onClick {
            // Handle Google login operation
        }

        activity_login.username.onClick {
            activity_login.username.text.clear()
        }

        activity_login.password.onClick {
            activity_login.password.text.clear()
        }
    }

    private fun hideStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun checkUsernameField() : Boolean {
        return !activity_login.username.text.isEmpty()
    }

    private fun checkPasswordField() : Boolean {
        return !activity_login.password.text.isEmpty()
    }
}
