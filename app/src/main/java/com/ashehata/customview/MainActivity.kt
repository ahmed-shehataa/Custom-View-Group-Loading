package com.ashehata.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startHandler()


        btn.setOnClickListener {
            rl_loading.loadingProgress(true)
            startHandler()
        }


    }


    private fun startHandler() {
        Handler(Looper.getMainLooper()).postDelayed({
            rl_loading.loadingProgress(false)

        }, 10000)

    }


}