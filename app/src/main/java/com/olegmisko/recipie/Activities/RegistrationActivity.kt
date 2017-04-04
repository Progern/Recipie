package com.olegmisko.recipie.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.olegmisko.recipie.R
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_registration.view.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast

class RegistrationActivity : AppCompatActivity() {

    private lateinit var firebaseAuthenticationManager: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        setContentView(R.layout.activity_registration)

        firebaseAuthenticationManager = FirebaseAuth.getInstance()


        activity_registration.submit_button.onClick {
            if (checkCredentials()) {
                registerUser(activity_registration.email.text.toString(), activity_registration.password.text.toString())
            } else {

            }
        }
    }


    private fun checkField(field: EditText): Boolean {
        return !field.text.isEmpty()
    }

    /*
        Due to that Firebase registration requires passwords
        with at least 6 characters length.
     */
    private fun checkPasswordFieldForLength() : Boolean {
        return activity_registration.password.text.toString().trim().length >= 6
    }

    /*
        Checks username field, password field and password length
        step-by-step and guides user, if some errors occur.
     */
    private fun checkCredentials() : Boolean {
        if (!checkField(activity_registration.email)) {
            toast("Email field is required.")
            return false
        }

        if (!checkField(activity_registration.password)) {
            toast("Password field is required.")
            return false
        }

        if (!checkPasswordFieldForLength()) {
            toast("Password must be at least 6 characters length.")
            return false
        }

        return true
    }

    /*
        Sends a registration request to firebase and tells user
        whether it was successful or not.
     */
    private fun registerUser(email: String, password: String) {
        firebaseAuthenticationManager.createUserWithEmailAndPassword(email.trim(), password.trim())
                .addOnCompleteListener {
                    toast("Registration successful")
                    // Login user, write to user defaults new state
                }

                .addOnFailureListener {
                    toast("Error occurred. Please, try again later")
                }


    }

    /*
        Hides the app bar and the status bar
     */
    private fun hideStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}
