package dev.echirchir.binaria.domain

import android.content.Context
import android.net.ConnectivityManager

class InternetServiceImpl(private val context: Context): InternetService {
    override fun isConnected(): Boolean {
        return try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = connectivityManager.activeNetworkInfo
            netInfo != null && netInfo.isConnectedOrConnecting
        } catch (e: NullPointerException) {
            e.printStackTrace()
            false
        }
    }
}