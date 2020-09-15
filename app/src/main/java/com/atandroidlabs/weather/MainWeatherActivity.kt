package com.atandroidlabs.weather

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainWeatherActivity : AppCompatActivity() {

    lateinit var bigCity1Temp: TextView
    lateinit var bigCity2Temp: TextView
    lateinit var bigCity3Temp: TextView
    lateinit var bigCity4Temp: TextView
    lateinit var cityIcon: ImageView
    lateinit var bigCity1Icon: ImageView
    lateinit var bigCity2Icon: ImageView
    lateinit var bigCity3Icon: ImageView
    lateinit var bigCity4Icon: ImageView
    lateinit var city: TextView
    lateinit var cityTemp: TextView
    lateinit var cityDescription: TextView
    lateinit var sp: SharedPreferences
    private lateinit var yourCity: String

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        if (item.itemId == R.id.change) {
            val intent: Intent = Intent(this@MainWeatherActivity, CityChangeActivity::class.java)
            startActivity(intent)
            finish()
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = MenuInflater(this@MainWeatherActivity)
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_weather)

        sp = getSharedPreferences("city", MODE_PRIVATE)
        yourCity =  sp.getString("your_city", "New Delhi")!!
        city = findViewById(R.id.city_name)
        cityTemp = findViewById(R.id.city_temp)
        cityDescription = findViewById(R.id.city_description)
        bigCity1Temp = findViewById(R.id.big_city1_temp)
        bigCity2Temp = findViewById(R.id.big_city2_temp)
        bigCity3Temp = findViewById(R.id.big_city3_temp)
        bigCity4Temp = findViewById(R.id.big_city4_temp)

        val loadCityDataTextView: TextView = findViewById(R.id.load_your_city_data)
        val content = SpannableString("Load next 5 days data of $yourCity")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        loadCityDataTextView.text = content

        cityIcon = findViewById(R.id.weather_icon)
        bigCity1Icon = findViewById(R.id.big_city1_icon)
        bigCity2Icon = findViewById(R.id.big_city2_icon)
        bigCity3Icon = findViewById(R.id.big_city3_icon)
        bigCity4Icon = findViewById(R.id.big_city4_icon)

        val task1: DownloadTask = DownloadTask(
            this@MainWeatherActivity,
            bigCity1Temp,
            null,
            bigCity1Icon
        )
        val task2: DownloadTask = DownloadTask(
            this@MainWeatherActivity,
            bigCity2Temp,
            null,
            bigCity2Icon
        )
        val task3: DownloadTask = DownloadTask(
            this@MainWeatherActivity,
            bigCity3Temp,
            null,
            bigCity3Icon
        )
        val task4: DownloadTask = DownloadTask(
            this@MainWeatherActivity,
            bigCity4Temp,
            null,
            bigCity4Icon
        )
        val task5: DownloadTask = DownloadTask(
            this@MainWeatherActivity,
            cityTemp,
            cityDescription,
            cityIcon
        )

        if (!InternetCheck.isNetworkAvailable(this@MainWeatherActivity)) {
            Toast.makeText(this@MainWeatherActivity,"Please check your internet connection",Toast.LENGTH_SHORT).show()
        } else {
            try {
                city.text = yourCity
                val newYork = "New York"
                val stringUrl =
                    "http://api.openweathermap.org/data/2.5/weather?q=$yourCity&APPID=" + AppId.appId
                val stringUrl1 =
                    "http://api.openweathermap.org/data/2.5/weather?q=$newYork&APPID=" + AppId.appId
                val stringUrl2 =
                    "http://api.openweathermap.org/data/2.5/weather?q=London&APPID=" + AppId.appId
                val stringUrl3 =
                    "http://api.openweathermap.org/data/2.5/weather?q=Sydney&APPID=" + AppId.appId
                val stringUrl4 =
                    "http://api.openweathermap.org/data/2.5/weather?q=Kolkata&APPID=" + AppId.appId

                task1.execute(stringUrl1)
                task2.execute(stringUrl2)
                task3.execute(stringUrl3)
                task4.execute(stringUrl4)
                task5.execute(stringUrl)

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MainWeatherActivity, e.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText(
                    this@MainWeatherActivity,
                    "Something went wrong, wrong city or loss of internet connection",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun searchCity(view: View) {
        if (InternetCheck.isNetworkAvailable(this@MainWeatherActivity)) {
            val searchText: EditText = findViewById(R.id.search_city_editText)
            val mgr = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            mgr.hideSoftInputFromWindow(searchText.windowToken, 0)
            if (searchText.text.isEmpty()) {
                Toast.makeText(this@MainWeatherActivity, "Please enter a city name", Toast.LENGTH_SHORT).show()
            } else {
                val cityNameGiven = searchText.text.toString().substring(0,1).toUpperCase() + searchText.text.toString().substring(1)
                downloadFiveDaysTaskStart(cityNameGiven)
            }
        } else {
            Toast.makeText(this@MainWeatherActivity, "Please check your internet connection", Toast.LENGTH_SHORT).show()
        }
    }

    fun loadFiveDaysDataForYourCity(view: View) {
        if (InternetCheck.isNetworkAvailable(this@MainWeatherActivity))
            downloadFiveDaysTaskStart(yourCity)
        else
            Toast.makeText(this@MainWeatherActivity, "Please check your internet connection", Toast.LENGTH_SHORT).show()
    }

    private fun downloadFiveDaysTaskStart(givenCity: String) {
        val task2: DownloadFiveDayTask = DownloadFiveDayTask(
            this@MainWeatherActivity,
            givenCity
        )

        try {
            val url: String = "http://api.openweathermap.org/data/2.5/forecast?q=${givenCity}&appid=${AppId.appId}"
            var data = task2.execute(url)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(
                this@MainWeatherActivity,
                "Something went wrong, Please try again",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}