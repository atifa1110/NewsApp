package com.example.projekakhir.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.projekakhir.R

class SplashActivity : AppCompatActivity() {

    private lateinit var appLogo : ImageView
    private lateinit var appName : TextView

    private lateinit var animation : Animation
    private lateinit var mDelayHandler : Handler
    private val SPLASH_DELAY : Long = 3000;

    internal val mRunnable : Runnable = Runnable {
        if(!isFinishing){
            navigateToActivitiy()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        appLogo = findViewById(R.id.app_logo)
        appName = findViewById(R.id.app_name)

        animation = AnimationUtils.loadAnimation(this@SplashActivity,R.anim.splash_anim)
        appLogo.startAnimation(animation)
        appName.startAnimation(animation)

        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable,SPLASH_DELAY)
    }

    private fun navigateToActivitiy() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}