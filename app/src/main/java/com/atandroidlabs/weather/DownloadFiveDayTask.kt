package com.atandroidlabs.weather

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.FileNotFoundException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class DownloadFiveDayTask(var context: Context, var city: String) : AsyncTask<String, Void, String>(){

    lateinit var progressDialog: ProgressDialog

    override fun onPreExecute() {
        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Loading 5 days data...")
        progressDialog.setMessage("Please wait")
        progressDialog.show()
    }

    override fun doInBackground(vararg urls: String?): String? {
        var result: String = ""
        val url : URL
        val connection: HttpURLConnection
        try {
            url = URL(urls[0])
            connection = url.openConnection() as HttpURLConnection
            val inputStream: InputStream = connection.inputStream
            val reader: InputStreamReader = InputStreamReader(inputStream)
            var data = reader.read()
            while (data != -1) {
                val current = data.toChar()
                result += current
                data = reader.read()
            }
            return result
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            return null
        }
        catch (e: FileNotFoundException) {
            e.printStackTrace()
            return null
        }
        catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    override fun onPostExecute(result: String?) {

        try{
            super.onPostExecute(result!!)

            val jsonObject: JSONObject = JSONObject(result!!)
            val jsonArray: JSONArray = jsonObject.getJSONArray("list")

            for (i in 0..jsonArray.length() - 1) {
                val jsonPart: JSONObject = jsonArray.get(i) as JSONObject
                val date: String = jsonPart.getString("dt_txt")
                val jsonMain = jsonPart.getJSONObject("main")
                val temp: Double = jsonMain.getDouble("temp") - 273.15
                val tempString: String = String.format("%.1f",temp) + " 'C"
                val weatherJSONArray: JSONArray = jsonPart.getJSONArray("weather")

                for(j in 0..weatherJSONArray.length() - 1) {
                    val weatherJSONObject: JSONObject = weatherJSONArray.get(j) as JSONObject
                    val des = weatherJSONObject.getString("description")
                    val icon: String = weatherJSONObject.getString("icon")
                    val imageTask1: DownloadWeatherIconTask = DownloadWeatherIconTask(context,date,tempString,des)
                    val iconUrl1 = "http://openweathermap.org/img/wn/$icon@2x.png"
                    imageTask1.execute(iconUrl1)
               }
            }

            progressDialog.dismiss()

            val intent: Intent = Intent(context ,SearchCityActivity::class.java)
            intent.putExtra("passed_city",city)
            context.startActivity(intent)
        } catch (e: FileNotFoundException) {
            Toast.makeText(context,"Wrong city",Toast.LENGTH_SHORT).show()
        }

        catch (e: Exception) {
            e.printStackTrace()
            progressDialog.dismiss()
            Toast.makeText(context,"Something went wrong, probably a wrong city or a malformed url",Toast.LENGTH_SHORT).show()
        }
    }
}