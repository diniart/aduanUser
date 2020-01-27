package com.example.thetwo_z

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.splash_layout.*
import android.view.Window
import android.view.WindowManager

class splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        window.requestFeature(Window.FEATURE_NO_TITLE)
        //making this activity full screen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.splash_layout)

        //4second splash_layout time
        Handler().postDelayed({
            //start main activity
            val intent = Intent(this, login::class.java)
            startActivity(intent)
            //finish this activity
            finish()
        },2000)
    }
}
