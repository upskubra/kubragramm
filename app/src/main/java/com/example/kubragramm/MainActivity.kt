package com.example.kubragramm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var signUpButton: Button // kayıt ol
    lateinit var signInButton: Button // giriş yap
    lateinit var userText: TextView
    lateinit var passwordText: TextView
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signUpButton = findViewById(R.id.signUpButton)
        signInButton = findViewById(R.id.signInButton)
        userText = findViewById(R.id.userText)
        passwordText = findViewById(R.id.passwordText)


        auth = FirebaseAuth.getInstance()

        signUpButton.setOnClickListener {
            signUp()
        }

        signInButton.setOnClickListener {
            signIn()
        }
    }


    fun signUp(){

        val email = userText.text.toString()
        val password = passwordText.text.toString()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val intent = Intent(this, FeedActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(applicationContext, "hoşgeldiniz ${email}", Toast.LENGTH_LONG).show()
            }

        }.addOnFailureListener{ exception ->
            Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()


        }



    }

    fun signIn(){

    }
}