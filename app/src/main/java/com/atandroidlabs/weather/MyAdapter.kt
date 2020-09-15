package com.atandroidlabs.weather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.lang.Exception

class MyAdapter( var contextOfApp: Context, var weatherList: ArrayList<Weather>, var resource: Int)  : ArrayAdapter<Weather>(contextOfApp, resource, weatherList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(contextOfApp)
        val view: View = inflater.inflate(resource,null,false)
        val tempTextView = view.findViewById<TextView>(R.id.weather_temp_recycler)
        val descriptionTextView: TextView = view.findViewById(R.id.weather_description_recycler)
        val dateTextView: TextView = view.findViewById(R.id.weather_date_recycler)
        val iconImageView: ImageView = view.findViewById(R.id.weather_icon_recycler)

        tempTextView.text = SearchCityActivity.weathers.get(position).temperture
        descriptionTextView.text = SearchCityActivity.weathers.get(position).description
        dateTextView.text = SearchCityActivity.weathers.get(position).date

        try {
            val imageTask1: DownloadImageTask = DownloadImageTask(context, iconImageView)
            val iconUrl1 = "http://openweathermap.org/img/wn/${weatherList.get(position).icon}icon@2x.png"
            val bitmap1 = imageTask1.execute(iconUrl1).get()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(contextOfApp, "Something went wrong while putting the data on list",Toast.LENGTH_SHORT).show()
        }
        return view
    }
}