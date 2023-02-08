package com.example.airqualityindex.features.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.airqualityindex.R
import com.example.airqualityindex.databinding.ActivitySplashScreenBinding
import com.example.airqualityindex.features.main.controller.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)

        this.bounceAnimation()

        val splashScreenTimeOut = 4000
        val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
        val additiveAnimator =
            Handler().postDelayed({
                startActivity(intent)
                finish()
            }, splashScreenTimeOut.toLong())
    }

    private val displayHeight: Int
        get() = this.resources.displayMetrics.heightPixels

    private fun bounceAnimation() {
        this.binding.imgHaveAGoodDay.clearAnimation()
        val transAnim = TranslateAnimation(
            -50f, 0f, -200f, 0f
            //(displayHeight / 2).toFloat()
        )
        transAnim.startOffset = 500
        transAnim.duration = 3000
        transAnim.fillAfter = true
        transAnim.interpolator = BounceInterpolator()
        transAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                this@SplashScreenActivity.binding.imgHaveAGoodDay.clearAnimation()
                val left = this@SplashScreenActivity.binding.imgHaveAGoodDay.left
                val top = this@SplashScreenActivity.binding.imgHaveAGoodDay.top
                val right = this@SplashScreenActivity.binding.imgHaveAGoodDay.right
                val bottom = this@SplashScreenActivity.binding.imgHaveAGoodDay.bottom
                this@SplashScreenActivity.binding.imgHaveAGoodDay.layout(left, top, right, bottom)
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

        })

        this.binding.imgHaveAGoodDay.startAnimation(transAnim)
    }

    private fun parallelRotationAnimation() {
        this.binding.imgHaveAGoodDay.animate().apply {
            duration = 3000
            rotationYBy(720f)
        }
    }

    private fun rotation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.splash_animation)
        this.binding.imgHaveAGoodDay.startAnimation(animation)
    }
}