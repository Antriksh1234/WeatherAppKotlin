package com.atandroidlabs.weather

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val handler: Handler = Handler()

        handler.postDelayed(Runnable() {
            if (InternetCheck.isNetworkAvailable(this@MainActivity)) {
                val intent: Intent = Intent(this@MainActivity, MainWeatherActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent: Intent = Intent(this@MainActivity, SorryNoInternet::class.java)
                startActivity(intent)
                finish()
            }

        }, 2000)
    }
}