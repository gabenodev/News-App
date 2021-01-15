package com.androiddevs.mvvmnewsapp.ui

import android.app.ProgressDialog.show
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.androiddevs.mvvmnewsapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

import kotlinx.android.synthetic.main.activity_login.tv_password as tv_password1


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        val register = findViewById<Button>(R.id.btn_resgister)
        btn_sign_up.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }

        btn_login.setOnClickListener{
            doLogin()
        }
    }

    private fun doLogin() {
        if (tv_username.text.toString().isEmpty()) {
            tv_username.error = "Please enter email"
            tv_username.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(tv_username.text.toString()).matches()) {
            tv_username.error = "Please enter valid email"
            tv_username.requestFocus()
            return
        }

        if (tv_password1.text.toString().isEmpty()) {
            tv_password1.error = "Please enter password"
            tv_password1.requestFocus()
            return
        }
        auth.signInWithEmailAndPassword(tv_username.toString(), tv_password1.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    public override fun onStart() {
            super.onStart()
            // Check if user is signed in (non-null) and update UI accordingly.
            val currentUser = auth.currentUser
            updateUI(currentUser)
        }

        private fun updateUI(currentUser: FirebaseUser?)
        {
            if(currentUser != null)
            {

                startActivity(Intent(this,NewsActivity::class.java))
                

            }
            else
            {
                Toast.makeText(

                   baseContext, "Login failed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
