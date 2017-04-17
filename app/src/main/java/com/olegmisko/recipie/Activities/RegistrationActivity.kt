package com.olegmisko.recipie.Activities

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.firebase.auth.FirebaseAuth
import com.olegmisko.recipie.Config.ActivityHelper
import com.olegmisko.recipie.Config.PREFS_NAME
import com.olegmisko.recipie.R
import com.olegmisko.recipie.Services.InternetConnectionService
import com.olegmisko.recipie.Services.LoginStateService
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_registration.view.*
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast

class RegistrationActivity : AppCompatActivity() {

    private lateinit var firebaseAuthenticationManager: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityHelper.hideStatusBar(this)
        setContentView(R.layout.activity_registration)

        firebaseAuthenticationManager = FirebaseAuth.getInstance()
        progressDialog = indeterminateProgressDialog("Registration in progress…")
        progressDialog.dismiss()


        activity_registration.submit_button.onClick {
            if (!InternetConnectionService.checkInternetConnection()) {
                InternetConnectionService.showNoConnectionDialog(this)
            } else {
                if (checkCredentials()) {
                    registerUser(activity_registration.email.text.toString(), activity_registration.password.text.toString())
                    progressDialog.show()
                } else {

                }
            }
        }

        activity_registration.alreadyRegistered.onClick {
            startActivity(intentFor<LoginActivity>())
        }
    }


    private fun checkField(field: EditText): Boolean {
        return !field.text.isEmpty()
    }

    /*
        Due to that Firebase registration requires passwords
        with at least 6 characters length.
     */
    private fun checkPasswordFieldForLength(): Boolean {
        return activity_registration.password.text.toString().trim().length >= 6
    }

    /*
        Checks username field, password field and password length
        step-by-step and guides user, if some errors occur.
     */
    private fun checkCredentials(): Boolean {
        if (!checkField(activity_registration.email)) {
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .playOn(activity_registration.email)
            toast("Email field is required.")
            return false
        }

        if (!checkField(activity_registration.password)) {
            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .playOn(activity_registration.password)
            toast("Password field is required.")
            return false
        }

        if (!checkPasswordFieldForLength()) {
            YoYo.with(Techniques.Pulse)
                    .duration(500)
                    .playOn(activity_registration.password)
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
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    toast("Registration successful.")
                    LoginStateService.changeUserStateToLoggedIn(getSharedPreferences(PREFS_NAME, 0))
                    startActivity(intentFor<MainActivity>())
                }

                .addOnFailureListener {
                    progressDialog.dismiss()
                    toast("Error occurred. Please, try again later.")
                }


    }

}
