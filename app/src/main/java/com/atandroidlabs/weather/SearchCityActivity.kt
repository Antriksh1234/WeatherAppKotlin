package com.atandroidlabs.weather

import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchCityActivity : AppCompatActivity() {

    lateinit var searchedCityName: TextView
    lateinit var searchedCityTemp: TextView
    lateinit var searchedCityDescription: TextView
    lateinit var searchedCityWeatherIcon: ImageView
    lateinit var recyclerView: RecyclerView
    
    companion object {
        lateinit var cityNameProvided: String
        lateinit var myWeatherAdapter: MyWeatherAdapter
        val weathers: ArrayList<Weather> = ArrayList<Weather>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_city)

        searchedCityName = findViewById(R.id.searched_city_name)
        searchedCityDescription = findViewById(R.id.searched_city_description)
        searchedCityTemp = findViewById(R.id._searched_city_temp)
        searchedCityWeatherIcon = findViewById(R.id._searched_weather_icon)
        
        recyclerView = findViewById(R.id.five_days_data)
        recyclerView.layoutManager = LinearLayoutManager(this)

        cityNameProvided = intent.getStringExtra("passed_city")!!
        searchedCityName.text = cityNameProvided
        
        myWeatherAdapter = MyWeatherAdapter(this@SearchCityActivity, cityNameProvided)
        recyclerView.adapter = myWeatherAdapter
        recyclerView.isNestedScrollingEnabled = false

        try {
            val task: DownloadTask = DownloadTask(
                this@SearchCityActivity,
                searchedCityTemp,
                searchedCityDescription,
                searchedCityWeatherIcon
            )
            val stringUrl = "http://api.openweathermap.org/data/2.5/weather?q=$cityNameProvided&APPID=${AppId.appId}"
            var result = task.execute(stringUrl)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(
                this@SearchCityActivity,
                "Something went wrong, please try again",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}