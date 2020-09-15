package com.atandroidlabs.weather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class SorryNoInternet : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sorry_no_internet)
    }

    fun tryAgain(view: View) {
        if (InternetCheck.isNetworkAvailable(this@SorryNoInternet)) {
            finish()
            val intent: Intent = Intent(this@SorryNoInternet,MainWeatherActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this@SorryNoInternet, "Please check your internet connection",Toast.LENGTH_SHORT).show()
        }
    }
}