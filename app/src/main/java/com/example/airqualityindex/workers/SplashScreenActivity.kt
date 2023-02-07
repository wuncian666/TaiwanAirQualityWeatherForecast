package com.example.airqualityindex.workers

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.airqualityindex.R
import com.example.airqualityindex.databinding.ActivitySplashScreenBinding


@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)

        val animation = AnimationUtils.loadAnimation(this, R.anim.splash_animation)
        binding.imgHaveAGoodDay.startAnimation(animation)

        val splashScreenTimeOut = 4000
        val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)

        Handler().postDelayed({
            startActivity(intent)
            finish()
        }, splashScreenTimeOut.toLong())
    }
}