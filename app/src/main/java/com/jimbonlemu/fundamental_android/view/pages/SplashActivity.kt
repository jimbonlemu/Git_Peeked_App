package com.jimbonlemu.fundamental_android.view.pages

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.jimbonlemu.fundamental_android.R
import com.jimbonlemu.fundamental_android.databinding.ActivitySplashBinding

@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivSplashScreen.startAnimation(
            AnimationUtils.loadAnimation(
                this@SplashActivity, R.anim.anim_fade_in_to_out
            )
        )

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 3000)

    }
}
