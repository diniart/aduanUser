package com.example.thetwo_z

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class login : AppCompatActivity() {

    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    private  var mAuth: FirebaseAuth?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //analytics
        mFirebaseAnalytics= FirebaseAnalytics.getInstance(this)
        //Auth
        mAuth= FirebaseAuth.getInstance()
        supportActionBar?.title = "Login"
        btnLogin.setOnClickListener{
            var email = etEmailLogin.text.toString()
            var password = etPasswordLogin.text.toString()
            mAuth!!.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    task->
                if (task.isSuccessful){
                    Toast.makeText(applicationContext,"Login sukses!!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Homee::class.java)
                    intent.flags =Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)

                }else{
                    Toast.makeText(applicationContext,"Login gagal!!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        toRegister.setOnClickListener {

            val intent = Intent(this,register::class.java)
            startActivity(intent)
            //
        }

    }

}
