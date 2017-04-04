package com.olegmisko.recipie.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import com.olegmisko.recipie.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*
import org.jetbrains.anko.onClick


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        setContentView(R.layout.activity_login)

        activity_login.submit_button.onClick {
            if (checkField(activity_login.email) && checkField(activity_login.password)) {
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

    }

    private fun hideStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun checkField(field: EditText): Boolean {
        return !field.text.isEmpty()
    }
}
