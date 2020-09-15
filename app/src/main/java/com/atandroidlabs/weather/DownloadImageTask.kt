package com.atandroidlabs.weather

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import android.widget.Toast
import java.io.InputStream
import java.net.URL

class DownloadImageTask(var context: Context, var img: ImageView) : AsyncTask<String, Void, Bitmap>() {
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

    override fun onPostExecute(result: Bitmap?) {
        super.onPostExecute(result)
        try{
            //iconList.add(result!!)
            img.setImageBitmap(result)
        } catch (e: Exception) {
            Toast.makeText(context,"Something went wrong, icon can't be fetched", Toast.LENGTH_SHORT).show()
        }
    }
}