package com.olegmisko.recipie.Activities

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.firebase.auth.FirebaseAuth
import com.olegmisko.recipie.Config.ActivityHelper
import com.olegmisko.recipie.Config.PREFS_NAME
import com.olegmisko.recipie.R
import com.olegmisko.recipie.Services.InternetConnectionService
import com.olegmisko.recipie.Services.LoginStateService
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*
import org.jetbrains.anko.*


class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuthenticationManager: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityHelper.hideStatusBar(this)
        setContentView(R.layout.activity_login)

        firebaseAuthenticationManager = FirebaseAuth.getInstance()
        progressDialog = indeterminateProgressDialog("Verifying credentials…")
        progressDialog.dismiss()


        activity_login.submit_button.onClick {
            if (!InternetConnectionService.checkInternetConnection()) {
                InternetConnectionService.showNoConnectionDialog(this)
            } else {
                if (checkField(activity_login.email) && checkField(activity_login.password)) {
                    userLogin(activity_login.email.text.toString(), activity_login.password.text.toString())
                    progressDialog.show()
                } else if (!checkField(activity_login.email)){
                    YoYo.with(Techniques.Shake)
                            .duration(500)
                            .playOn(activity_login.email)
                    toast("Input your login.")
                } else if (!checkField(activity_login.password)) {
                    YoYo.with(Techniques.Shake)
                            .duration(500)
                            .playOn(activity_login.password)
                    toast("Input your password.")
                }
            }
        }

        activity_login.forgotPassword.onClick {
            alert {
                customView {
                    verticalLayout {
                        title("Please input your email")
                        message("A message with reset guide will be sent to you.")

                        val email = editText {
                            hint = "Email"
                        }

                        positiveButton("Submit") {
                            if (!email.text.toString().trim().isEmpty()) {
                                firebaseAuthenticationManager.sendPasswordResetEmail(email.text.toString().trim())
                                toast("Message sent. Please, check your email.")
                            }

                        }

                        negativeButton("Cancel") {
                            dismiss()
                        }
                    }
                }
            }.show()
        }

        activity_login.not_registered_yet.onClick {
            startActivity(intentFor<RegistrationActivity>())
        }

        /*
        activity_login.facebook_submit.onClick {
            // Handle Facebook login operation
        }

        activity_login.google_submit.onClick {
            // Handle Google login operation
        } */

    }

    /*
        Sends a request to Firebase with specified credentials
        and guides user to the next Activity
     */
    private fun userLogin(email: String, password: String) {
        firebaseAuthenticationManager.signInWithEmailAndPassword(email, password)

                .addOnSuccessListener {
                    progressDialog.dismiss()
                    toast("Logged in successfully.")
                    LoginStateService.changeUserStateToLoggedIn(getSharedPreferences(PREFS_NAME, 0))
                    startActivity(intentFor<MainActivity>())
                }

                .addOnFailureListener {
                    progressDialog.dismiss()
                    toast("Error occurred. Please, try again.")
                }


    }


    private fun checkField(field: EditText): Boolean {
        return !field.text.isEmpty()
    }

}
