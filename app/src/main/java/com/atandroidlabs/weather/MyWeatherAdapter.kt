package com.atandroidlabs.weather

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

class MyWeatherAdapter(var context: Context, var city: String) : RecyclerView.Adapter<MyWeatherAdapter.MyWeatherViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyWeatherAdapter.MyWeatherViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.weather_recycler_item,parent,false)
        return MyWeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyWeatherAdapter.MyWeatherViewHolder, position: Int) {

        try {
            val date: String = SearchCityActivity.weathers.get(position).date
            val description: String = SearchCityActivity.weathers.get(position).description
            val icon: Bitmap = SearchCityActivity.weathers.get(position).icon
            val temp: String = SearchCityActivity.weathers.get(position).temperture

            holder.dateTextView.text = date
            holder.tempTextView.text = temp
            holder.descriptionTextView.text = description
            holder.iconImageView.setImageBitmap(icon)
        } catch (e: Exception) {
            Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return SearchCityActivity.weathers.size
    }

    class MyWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var dateTextView: TextView
        lateinit var tempTextView: TextView
        lateinit var iconImageView: ImageView
        lateinit var descriptionTextView: TextView

        init {
            dateTextView = itemView.findViewById(R.id.weather_date_recycler)
            tempTextView = itemView.findViewById(R.id.weather_temp_recycler)
            iconImageView = itemView.findViewById(R.id.weather_icon_recycler)
            descriptionTextView = itemView.findViewById(R.id.weather_description_recycler)
        }
    }
}