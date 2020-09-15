package com.atandroidlabs.weather

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


class InternetCheck {
    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}