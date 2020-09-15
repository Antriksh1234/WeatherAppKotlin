package com.atandroidlabs.weather

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.Toast
import java.io.FileNotFoundException
import java.io.InputStream
import java.net.URL

class DownloadWeatherIconTask(var context: Context, var date: String, var temp: String, var des: String) : AsyncTask<String, Void, Bitmap>() {
    override fun doInBackground(vararg urls: String?): Bitmap? {
        val icon: Bitmap
        val url = urls[0]
        return try {
            val inputStream: InputStream = URL(url).openStream()
            icon = BitmapFactory.decodeStream(inputStream)
            icon
        } catch(e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun onPostExecute(icon: Bitmap?) {
        try {
            super.onPostExecute(icon!!)
            addToWeatherList(icon)
        }
        catch (e: FileNotFoundException) {
            Toast.makeText(context, "Wrong city name!",Toast.LENGTH_SHORT).show()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addToWeatherList(icon: Bitmap) {
        SearchCityActivity.weathers.add(Weather(date,temp,icon,des))
        //SearchCityActivity.myWeatherAdapter.notifyDataSetChanged()
    }
}