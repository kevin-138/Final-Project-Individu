package com.kevin.netkick.presentation

import androidx.appcompat.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.kevin.netkick.R
import com.kevin.netkick.presentation.view.home.fragments.ExploreFragment
import com.kevin.netkick.presentation.view.home.fragments.HomeFragment
import com.kevin.netkick.presentation.view.home.fragments.TrophiesFragment
import java.util.*
import kotlin.concurrent.schedule


object PresentationUtils {

    const val LIVE_PARAMS = "all"

    const val HOME = "001"
    const val EXPLORE = "002"
    const val TROPHIES = "003"

    fun isOnline(activity: FragmentActivity): Boolean {
        val connectivityManager =
            activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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
        return false}

    fun networkDialog(activity: FragmentActivity, currentFrag: String){
        val dialogBuilder = AlertDialog.Builder(activity, R.style.NetworkAlertDialogTheme)
        dialogBuilder.setMessage("No Network Connection detected, Please make sure you have a stable connection to the internet, then press retry to refresh the app and try again.")
        dialogBuilder.setCancelable(false)
        dialogBuilder.setIcon(R.drawable.no_internet_logo)
        dialogBuilder.setTitle("No Network Connection")
        dialogBuilder.setPositiveButton("RETRY") { _, _ ->
            var homeFragment = false
            var expFragment = false
            var tropFragment = false

            when(currentFrag){
                HOME -> homeFragment = true

                EXPLORE ->  expFragment = true

                TROPHIES -> tropFragment = true
            }
            Timer().schedule(1000L) {
                activity.runOnUiThread {
                    (activity.supportFragmentManager.fragments[0] as HomeFragment).checkOnline(homeFragment)
                    (activity.supportFragmentManager.fragments[1] as ExploreFragment).checkOnline(expFragment)
                    (activity.supportFragmentManager.fragments[2] as TrophiesFragment).checkOnline(tropFragment)
                }
            }
        }
        dialogBuilder.setNegativeButton("CLOSE APP") { _, _ ->
            activity.finish()
        }
        val connectionAlertDialog = dialogBuilder.create()
        connectionAlertDialog.window?.setBackgroundDrawableResource(R.drawable.connection_dialog_background)
        connectionAlertDialog.show()
    }
}