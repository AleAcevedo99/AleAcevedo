package com.example.aleacevedo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide();

        Handler().postDelayed({
            val intent = Intent(this@SplashScreenActivity, LogInActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000L )
    }

}