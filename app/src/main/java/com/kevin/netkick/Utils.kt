package com.kevin.netkick

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.fragment.app.FragmentActivity


object Utils {
    const val FOOTBALL_API_KEY = "x-apisports-key: c544dcdd442a1c5159c476243f1299d8"
    const val FOOTBALL_API_BASE = "https://v3.football.api-sports.io/"
    const val LIVE_PARAMS = "all"

    const val NEWS_API_KEY = "1f0d9449fae149998f765270b1c23cbc"
    const val NEWS_API_BASE = "https://newsapi.org/v2/"

    fun isOnline(activity: FragmentActivity): Boolean {
        val connectivityManager =
            activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }else{
            val dialogBuilder = AlertDialog.Builder(activity)
            dialogBuilder.setMessage("No Network Connection detected, press retry to refresh the app and try again. Please make sure you have a stable connection to the internet")
            dialogBuilder.setCancelable(false)
            dialogBuilder.setPositiveButton("Retry") { dialog, which ->
                activity.recreate()
            }
            dialogBuilder.setNegativeButton("cancel") { dialog, which ->
                dialog.cancel()
            }
            val deleteDialog = dialogBuilder.create()
            deleteDialog.setTitle("No Network Connection")
            deleteDialog.setIcon(R.drawable.no_internet_logo)
            deleteDialog.show()
        }
        return false}
}