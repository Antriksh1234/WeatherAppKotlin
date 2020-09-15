package com.atandroidlabs.weather

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class CityChangeActivity : AppCompatActivity() {

    override fun onBackPressed() {
        val intent = Intent(this@CityChangeActivity, MainWeatherActivity::class.java)
        startActivity(intent)
        finish()
    }

    lateinit var cityName: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_change)

        cityName = findViewById(R.id.city_name_editText)
    }

    fun saveCity(view: View) {
        val sp: SharedPreferences = getSharedPreferences("city", MODE_PRIVATE)
        if (cityName.text.isEmpty()) {
            Toast.makeText(this@CityChangeActivity,"Please enter a city name",Toast.LENGTH_SHORT).show()
        } else {
            val cityNameGiven = cityName.text.toString().substring(0,1).toUpperCase() + cityName.text.toString().substring(1)
            sp.edit().putString("your_city", cityNameGiven).apply()
            Toast.makeText(this@CityChangeActivity,"Changed your default city to ${cityName.text}",Toast.LENGTH_SHORT).show()
            finish()
            val intent: Intent = Intent(this@CityChangeActivity,MainWeatherActivity::class.java)
            startActivity(intent)
        }
    }
}