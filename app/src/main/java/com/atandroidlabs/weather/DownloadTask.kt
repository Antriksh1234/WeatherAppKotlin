package com.atandroidlabs.weather


import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class DownloadTask(var context: Context, var tempTxt: TextView, var des: TextView?, var img: ImageView) : AsyncTask<String, Void, String>() {

    lateinit var progressDialog: ProgressDialog

    override fun onPreExecute() {
        super.onPreExecute()
        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Loading...")
        progressDialog.setMessage("Please wait..")
        progressDialog.show()
    }

    override fun doInBackground(vararg urls: String?): String? {
        var result: String = ""
        val url: URL
        val connection: HttpURLConnection
        try {
            url = URL(urls[0])
            connection = url.openConnection() as HttpURLConnection
            val inputStream: InputStream = connection.getInputStream()
            val reader: InputStreamReader = InputStreamReader(inputStream)
            var data = reader.read()
            while (data != -1) {
                val current: Char = data.toChar()
                result += current
                data = reader.read()
            }
            return result
        }
        catch (e: MalformedURLException) {
            e.printStackTrace()
            return null
        }
        catch(e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    override fun onPostExecute(result: String?) {
        try {
            super.onPostExecute(result!!)

            val jsonObject: JSONObject = JSONObject(result)
            val weatherInfo = jsonObject.getString("weather")

            val jsonArray: JSONArray = JSONArray(weatherInfo)

            for (i in 0..jsonArray.length() - 1) {
                var jsonPart: JSONObject = jsonArray.get(i) as JSONObject
                val description: String = jsonPart.getString("description")
                val icon: String = jsonPart.getString("icon")

                val imageTask1: DownloadImageTask = DownloadImageTask(context, img)
                val iconUrl1 = "http://openweathermap.org/img/wn/$icon@2x.png"
                imageTask1.execute(iconUrl1)

                des?.setText(description)
            }

            val tempJSON: JSONObject = jsonObject.getJSONObject("main")
            val temp: Double = tempJSON.getDouble("temp") - 273.15
            val tempString = String.format("%.1f",temp) + " 'C"
            tempTxt.setText(tempString)
            progressDialog.dismiss()
        } catch(e: Exception) {
            progressDialog.dismiss()
            Toast.makeText(context,"Something went wrong probably a wrong city or a malformed url",Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
}
